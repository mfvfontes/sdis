package com.fireflies.network.messages;

import com.fireflies.reference.Reference;

public class DeleteChunk extends Message{

	public DeleteChunk(String fileID){
		this.msgType = Reference.msgDelete;
		this.version = Reference.version;
		this.fileID = fileID;
	}
	
	@Override
    public byte[] getBytes() {
        String ret = msgType + " " + version + " " + fileID + separator + separator;
        return ret.getBytes();
    }
}