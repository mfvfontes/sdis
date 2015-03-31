package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;

import java.sql.Ref;

/**
 * Created by João on 19/03/2015.
 */
public class Message {

    private static String separator = Character.toString((char)0xA) + Character.toString((char)0xD);
    private String msgType = null;
    private Double version = null;
    private String fileID = null;
    private Integer chunkNo = null;
    private Integer replication = null;
    private Chunk chunk;


    public Message(byte[] buffer)
    {
        parse(buffer);
    }

    private void parse (byte[] buffer)
    {
        String msg = new String (buffer);
        String[] msgArray = msg.split(separator+separator);

        String header = msgArray[1];
        String[] headerArray = header.split(" ");

        if (headerArray[0].equalsIgnoreCase(Reference.msgPutChunk))
            parsePutChunk(headerArray);
            /*
            TODO:
            Other parses
            */
    }

    private void parsePutChunk(String[] array)
    {
        msgType = Reference.msgPutChunk;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        replication = Integer.parseInt(array[3]);
    }

    public byte[] getBytes() {
        String ret = null;

        if (msgType != null)
            ret += msgType;

        if (version != null)
            ret += version;

        if (fileID != null)
            ret += fileID;

        if (chunkNo != null)
            ret += chunkNo;

        if (replication != null)
            ret += replication;

        if (chunk != null)
            ret += (separator + separator + chunk.toString());

        return ret.getBytes();
    }
}
