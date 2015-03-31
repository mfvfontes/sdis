package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;

import java.sql.Ref;

/**
 * Created by João on 19/03/2015.
 */
public class Message {

    protected static String separator = Character.toString((char)0xA) + Character.toString((char)0xD);
    protected String msgType = null;
    protected Double version = null;
    protected String fileID = null;
    protected Integer chunkNo = null;
    protected Integer replication = null;
    protected Chunk chunk = null;


    private void parse (byte[] buffer)
    {
        String msg = new String (buffer);
        String[] msgArray = msg.split(separator+separator);

        String header = msgArray[0];
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
        return null;
    }

}
