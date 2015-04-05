package com.fireflies.threads;

import com.fireflies.File;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.GetChunk;
import com.fireflies.reference.Reference;

import java.net.InetAddress;

public class ChunkRestore extends Thread{

	String fileId;
	Integer chunkNo;
	byte[] data;
	final Object lock = new Object();

	
	public ChunkRestore(String fileId, Integer chunkNo){
		this.fileId = fileId;
		this.chunkNo = chunkNo;
		data = new byte[Reference.chunkSize];
	}
	
	@Override
	public void run(){
		
		System.out.println("Restore Thread " + chunkNo);
		
		GetChunk msg = new GetChunk(fileId,chunkNo);
		
		NetworkHandler.sendToMC(msg);

		synchronized (lock)
		{
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}
}
