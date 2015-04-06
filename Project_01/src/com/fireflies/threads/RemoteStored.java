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

        //System.out.println("Remote Stored Thread for chunk no " + message.chunkNo + " from file " + message.fileID);

        ChunkID id = new ChunkID(message.fileID,message.chunkNo);
        if (LibraryHandler.fileLibrary.haveFile(message.fileID))
        {
            if (!LibraryHandler.fileLibrary.peerAlreadyHadChunk(id,message.address)) {
                LibraryHandler.fileLibrary.addStoredPeer(id,message.address);
                LibraryHandler.fileLibrary.incChunkReplication(id);
            }
        }

        if (LibraryHandler.fileLibrary.haveStoredChunk(id))
        {
            LibraryHandler.fileLibrary.incStoredChunkReplication(id,message.address);
        }

        LibraryHandler.saveLibrary();
    }
}
