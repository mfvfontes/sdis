package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.LibraryHandler;
import com.fireflies.network.messages.Message;

/**
 * Created by João on 04/04/2015.
 */
public class RemoteStored extends Thread {

    Message message;

    public RemoteStored (Message message)
    {
        this.message = message;
    }

    @Override
    public void run()
    {
        if (LibraryHandler.fileLibrary.haveFile(message.fileID))
        {
            LibraryHandler.fileLibrary.incChunkReplication(new ChunkID(message.fileID,message.chunkNo));
            System.out.println("Chunk " + message.chunkNo + " replication increased to " + LibraryHandler.fileLibrary.getChunkReplication(new ChunkID(message.fileID,message.chunkNo)));
        }
    }
}
