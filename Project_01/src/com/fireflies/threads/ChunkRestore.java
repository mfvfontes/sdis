package com.fireflies.threads;

import com.fireflies.File;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.GetChunk;
import com.fireflies.reference.Reference;

import java.net.InetAddress;
import java.util.Random;

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

		//System.out.println("Restore Thread " + chunkNo + " file " + fileId);

		int port = (new Random().nextInt(4000)+4000);

		GetChunk msg = new GetChunk(fileId,chunkNo,port);

		ChunkReceivedUDP chunkReceivedUDP = new ChunkReceivedUDP(port,this);
		chunkReceivedUDP.start();
		
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
