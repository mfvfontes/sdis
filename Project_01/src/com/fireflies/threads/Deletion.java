package com.fireflies.threads;

import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.DeleteChunk;

public class Deletion extends Thread {

	String fileID;
	
	public Deletion(String file){

		this.fileID = LibraryHandler.fileLibrary.getFileID(file);
	}
	
	@Override
	public void run(){
		
		System.out.println("Deletion Thread");

		DeleteChunk msg = new DeleteChunk(fileID);
		
		NetworkHandler.sendToMC(msg);

		LibraryHandler.fileLibrary.removeFile(fileID);

	}
	
}
