package com.fireflies.network.messages;

import com.fireflies.File;
import com.fireflies.reference.Reference;

/**
 * Created by João on 28/03/2015.
 */
public class PutChunk extends Message {

    public PutChunk(Integer chunkNo, File file)
    {
        this.msgType = Reference.msgPutChunk;
        this.version = Reference.version;
        this.fileID = file.getFileID();
        this.replication = file.getReplication();
        this.chunkNo = chunkNo;
        this.chunk = file.getChunks().get(chunkNo);
    }

    @Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + " " + chunkNo + " " + replication + separator + separator + chunk;
        return ret.getBytes();
    }
}
