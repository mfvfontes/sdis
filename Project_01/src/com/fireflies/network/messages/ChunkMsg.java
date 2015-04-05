package com.fireflies.network.messages;

import com.fireflies.Chunk;
import com.fireflies.reference.Reference;

/**
 * Created by João on 04/04/2015.
 */
public class ChunkMsg extends Message {

    public ChunkMsg (int chunkNo, String fileID, byte[] data)
    {
        this.msgType = Reference.msgChunk;
        this.version = Reference.version;
        this.fileID = fileID;
        this.chunkNo = chunkNo;
        this.data = data;
    }

    @Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + " " + chunkNo + separator + separator;

        byte[] headerBytes = ret.getBytes();
        byte[] msgBytes = new byte[headerBytes.length + data.length];
        System.arraycopy(headerBytes,0,msgBytes,0,headerBytes.length);
        System.arraycopy(data,0,msgBytes,headerBytes.length,data.length);

        return msgBytes;
    }
}
