package com.fireflies.network.messages;

import com.fireflies.reference.Reference;

public class Removed extends Message{

	public Removed(String fileID, Integer chunkNo){
		this.msgType = Reference.msgRemoved;
		this.version = Reference.version;
		this.fileID = fileID;
		this.chunkNo = chunkNo;
	}
	
	@Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + " " + chunkNo + separator + separator;
        return ret.getBytes();
    }
}
