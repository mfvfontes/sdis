package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;

/**
 * Created by João on 28/03/2015.
 */
public class PutChunk extends Message {

    public PutChunk(Chunk chunk, String fileID, int replication)
    {
        this.msgType = Reference.msgPutChunk;
        this.version = Reference.version;
        this.fileID = fileID;
        this.replication = replication;
        this.chunkNo = chunk.getChunkNo();
        this.data = chunk.getData();
    }

    @Override
    public byte[] getBytes() {

        String ret = msgType + " " + version + " " + fileID + " " + chunkNo + " " + replication + separator + separator;
        byte[] headerBytes = ret.getBytes();
        byte[] msgBytes = new byte[headerBytes.length + data.length];
        System.arraycopy(headerBytes,0,msgBytes,0,headerBytes.length);
        System.arraycopy(data,0,msgBytes,headerBytes.length,data.length);
        return msgBytes;
    }
}
