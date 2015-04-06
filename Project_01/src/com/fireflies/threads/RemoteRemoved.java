package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.LibraryHandler;
import com.fireflies.network.messages.Message;

/**
 * Created by João on 04/04/2015.
 */
public class RemoteRemoved extends Thread {

    Message message;

    public RemoteRemoved(Message message)
    {
        this.message = message;
    }

    @Override
    public void run()
    {
        //System.out.println("Remote Removed Thread from file " + message.fileID + " chunk number " + message.chunkNo);

        ChunkID id = new ChunkID(message.fileID,message.chunkNo);

        // Se o ficheiro é meu
        if (LibraryHandler.fileLibrary.haveFile(message.fileID))
        {
            System.out.println("A chunk from one of my files was removed from the system");
            // Se o peer ainda tem o ficheiro (mensagens repetidas)
            if (LibraryHandler.fileLibrary.peerAlreadyHadChunk(id,message.address)) {
                // Apagar o peer da lista
                LibraryHandler.fileLibrary.removeStoredPeer(id, message.address);
                LibraryHandler.fileLibrary.decChunkReplication(id);
            }
        }

        // Se tenho uma cópia do chunk
        if (LibraryHandler.fileLibrary.haveStoredChunk(id))
        {
            // Decrementar a replicação
            LibraryHandler.fileLibrary.decStoredChunkReplication(id, message.address);
        }

//        System.out.println("Chunk " + message.chunkNo + " replication decreased to " + LibraryHandler.fileLibrary.getStoredActualReplication(new ChunkID(message.fileID, message.chunkNo)));
    }
}
