package com.fireflies.network.messages;

import com.fireflies.File;
import com.fireflies.reference.Reference;

public class DeleteChunk extends Message{

	public DeleteChunk(Double version, File file){
		this.msgType = Reference.msgDeleteChunk;
		this.version = version;
		this.fileID = file.getFileID();
	}
	
	@Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + separator + separator;
        return ret.getBytes();
    }
}