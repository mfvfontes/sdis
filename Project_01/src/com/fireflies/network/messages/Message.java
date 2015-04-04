package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;

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
    public Chunk chunk = null;

    public Message (byte[] buffer)
    {
        parse(buffer);
    }

    public Message() {
    }

    private void parse (byte[] buffer)
    {
        String msg = new String (buffer);
        String[] msgArray = msg.split(separator+separator,2);

        String header = msgArray[0];
        String[] headerArray = header.split(" ");

        if (headerArray[0].equalsIgnoreCase(Reference.msgPutChunk))
            parsePutChunk(headerArray,msgArray[1]);

        if (headerArray[0].equalsIgnoreCase(Reference.msgStored))
            parseStored(headerArray);

            /*
            TODO:
            Other parses
            */
    }

    private void parsePutChunk(String[] array, String chunk)
    {
        msgType = Reference.msgPutChunk;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
        chunkNo = Integer.parseInt(array[3]);
        replication = Integer.parseInt(array[3]);

        this.chunk = new Chunk(chunk.getBytes(),chunkNo,fileID);
    }

    private void parseStored(String[] array)
    {
        msgType = Reference.msgStored;
        version = Double.parseDouble(array[1]);
        fileID = array[2];
    }

    public byte[] getBytes() {
        return null;
    }

}
