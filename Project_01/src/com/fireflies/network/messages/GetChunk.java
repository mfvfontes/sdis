package com.fireflies.network.messages;

import com.fireflies.File;
import com.fireflies.reference.Reference;

public class GetChunk extends Message{

	public GetChunk(Double version, Integer chunkNo, File file){
		this.msgType = Reference.msgGetChunk;
		this.version = version;
		this.fileID = file.getFileID();
		this.chunkNo = chunkNo;
		this.chunk = file.getChunks().get(chunkNo);
	}
	
	@Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + " " + chunkNo + separator + separator;
        return ret.getBytes();
    }
}
