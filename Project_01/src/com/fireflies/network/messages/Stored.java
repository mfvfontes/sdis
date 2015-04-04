package com.fireflies.network.messages;

import com.fireflies.reference.Reference;

/**
 * Created by João on 03/04/2015.
 */
public class Stored extends Message{

    public Stored (Integer chunkNo, String fileID)
    {
        this.msgType = Reference.msgStored;
        this.version = Reference.version;
        this.fileID = fileID;
        this.chunkNo = chunkNo;
    }

    @Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + " " + chunkNo;
        return ret.getBytes();
    }
}
