package com.fireflies.network.messages;

import com.fireflies.File;
import com.fireflies.reference.Reference;

public class GetChunk extends Message{

	public GetChunk(String fileID, Integer chunkNo){
		this.msgType = Reference.msgGetChunk;
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
