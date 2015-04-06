package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.File;
import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.PutChunk;
import com.fireflies.reference.Reference;

/**
 * Created by João on 03/04/2015.
 */
public class ChunkBackup extends Thread {

    int chunkNo;
    File file;

    public ChunkBackup(int chunkNo, File file) {
        this.chunkNo = chunkNo;
        this.file = file;
    }

    @Override
    public void run() {



        int nAttempts = 0;
        int waitTime = 500;

        ChunkID id = new ChunkID(file.getFileID(),chunkNo);
        int desiredReplication = file.getReplication();
        int actualReplication;

        //System.out.println("Chunk Backup Thread for chunk no " + chunkNo + " from file " + file.getFileID());

        do {
            PutChunk msg = new PutChunk(file.getChunks().get(chunkNo),file.getFileID(),file.getReplication());

            NetworkHandler.sendToMDB(msg);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            }

            nAttempts++;
            waitTime *= 2;

            actualReplication = LibraryHandler.fileLibrary.getChunkReplication(id);


        } while (
                (actualReplication < desiredReplication )
                &&
                (nAttempts < Reference.maxBackupAttempts)
                );
    }
}
