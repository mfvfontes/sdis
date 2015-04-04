package com.fireflies.threads;

import com.fireflies.File;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.DeleteChunk;
import com.fireflies.reference.Reference;

public class Deletion extends Thread {

	File file;
	
	public Deletion(File file){
		this.file = file;
	}
	
	@Override
	public void run(){
		
		System.out.println("Deletion Thread");

		DeleteChunk msg = new DeleteChunk(Reference.version, file);
		
		NetworkHandler.sendToMC(msg);
		
		System.out.println("Sent to MC - Delete file with fileID: " + file.getFileID());
		
	}
	
}
