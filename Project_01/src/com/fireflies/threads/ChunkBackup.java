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

        do {
            PutChunk msg = new PutChunk(chunkNo,file);
            NetworkHandler.sendToMDB(msg);

            System.out.println("Sent chunk no " + chunkNo);

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nAttempts++;
            waitTime *= 2;
        } while (
                (LibraryHandler.fileLibrary.getChunkReplication(new ChunkID(file.getFileID(),chunkNo)) < file.getReplication())
                &&
                (nAttempts < Reference.maxBackupAttempts)
                );
    }
}
