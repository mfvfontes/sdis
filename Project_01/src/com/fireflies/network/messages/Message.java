package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;
import sun.misc.IOUtils;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.sql.Ref;
import java.util.Arrays;

/**
 * Created by João on 19/03/2015.
 */

public class Message {

    protected static String separator = Character.toString((char)0xA) + Character.toString((char)0xD);
    public String msgType = null;
    public Double version = null;
    public String fileID = null;
    public Integer chunkNo = null;
    public Integer replication = null;
    public byte[] data = null;
    public Integer port = null;

    public InetSocketAddress address = null;

    public Message (byte[] buffer, InetSocketAddress address)
    {
        this.address = address;
        parse(buffer);
    }

    public Message() {
    }

    private void parse (byte[] buffer)
    {
        String msg = null;

        try {

            String tmpmsg = new String(buffer,"ISO-8859-1");
            msg = tmpmsg.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] msgArray = msg.split(separator+separator,2);

        String[] headerLines = msgArray[0].split(separator);
        String[] headerArray = headerLines[0].split(" ");

        if (headerArray[0].equalsIgnoreCase(Reference.msgPutChunk))
            parsePutChunk(headerArray,msgArray[1]);

        if (headerArray[0].equalsIgnoreCase(Reference.msgStored))
            parseStored(headerArray);

        if (headerArray[0].equalsIgnoreCase(Reference.msgGetChunk))
            parseGetChunk(headerArray,headerLines[1]);

        if (headerArray[0].equalsIgnoreCase(Reference.msgChunk)) {
            if (msgArray[1] != null)
                parseChunk(headerArray, msgArray[1]);
            else
                parseGetChunk(headerArray, null);
        }

        if (headerArray[0].equalsIgnoreCase(Reference.msgDelete))
            parseDelete(headerArray);

        if (headerArray[0].equalsIgnoreCase(Reference.msgRemoved))
            parseRemoved(headerArray);
    }

    private void parseRemoved(String[] array)
    {
        msgType = Reference.msgRemoved;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);
    }

    private void parseDelete(String[] array)
    {
        msgType = Reference.msgDelete;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
    }

    private void parseChunk(String[] array, String chunk)
    {
        msgType = Reference.msgChunk;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);

        data = chunk.getBytes(Charset.forName("ISO-8859-1"));
    }

    private void parseGetChunk(String[] array, String secondLine)
    {

        msgType = Reference.msgGetChunk;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);

        if (secondLine != null) {
            String[] secondLineArgs = secondLine.split(" ");
            port = Integer.parseInt(secondLineArgs[0]);
        }
    }

    private void parsePutChunk(String[] array, String chunk)
    {
        msgType = Reference.msgPutChunk;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);
        replication = Integer.parseInt(array[4]);

        data = chunk.getBytes(Charset.forName("ISO-8859-1"));
    }

    private void parseStored(String[] array)
    {
        msgType = Reference.msgStored;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);
    }

    public byte[] getBytes() {
        return null;
    }

}
