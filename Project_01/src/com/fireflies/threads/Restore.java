package com.fireflies.threads;

import com.fireflies.File;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.GetChunk;
import com.fireflies.reference.Reference;

public class Restore extends Thread{

	File file;
	
	Integer chunkNo;
	
	public Restore(File file, Integer chunkNo){
		this.file = file;
		this.chunkNo = chunkNo;
	}
	
	@Override
	public void run(){
		
		System.out.println("Restore Thread");
		
		GetChunk msg = new GetChunk(Reference.version, chunkNo, file);
		
		NetworkHandler.sendToMC(msg);
		
		System.out.println("Sent message to MC with Chunk no:" + chunkNo);
	}
}
