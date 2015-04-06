package com.fireflies.threads;

import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.DeleteChunk;
import com.fireflies.reference.Reference;

public class Deletion extends Thread {

	String fileID;
	
	public Deletion(String fileID){
		this.fileID = fileID;
	}
	
	@Override
	public void run(){
		
		System.out.println("Deletion Thread for fileID " + fileID);

		DeleteChunk msg = new DeleteChunk(fileID);

		for (int i = 0; i < Reference.numberRepeatMessages; i++) {
			NetworkHandler.sendToMC(msg);
		}

		LibraryHandler.fileLibrary.removeFile(fileID);

		LibraryHandler.saveLibrary();

	}
	
}
