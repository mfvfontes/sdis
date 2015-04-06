package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.FileLibrary;
import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.Message;
import com.fireflies.network.messages.Stored;
import com.fireflies.reference.Reference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by João on 01/04/2015.
 */
public class Store extends Thread {

    Message putChunkMsg;

    public Store (Message putChunkMsg)
    {
        this.putChunkMsg = putChunkMsg;
    }

    @Override
    public void run() {

        //System.out.println("Store Thread for chunk no " + putChunkMsg.chunkNo + " from file " + putChunkMsg.fileID);

        if ((LibraryHandler.fileLibrary.availableSpace - putChunkMsg.data.length) < 0 )
        {
            System.out.println("Disk space necessary to store chunk not available");
            return;
        }

        if (LibraryHandler.fileLibrary.haveFile(putChunkMsg.fileID))
        {
            System.out.println("Cannot store my own file");
            return;
        }

        saveChunk();

        LibraryHandler.fileLibrary.addStoredChunk(new ChunkID(putChunkMsg.fileID, putChunkMsg.chunkNo),putChunkMsg.data.length,putChunkMsg.replication);

        Stored msg = new Stored(putChunkMsg.chunkNo,putChunkMsg.fileID);

        int timeToWait = new Random().nextInt(400);

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Reference.numberRepeatMessages; i++) {
            NetworkHandler.sendToMC(msg);
        }

        LibraryHandler.saveLibrary();
    }

    private void saveChunk()
    {
        try {
            FileOutputStream fos = new FileOutputStream(Reference.chunksFolder + "/" + putChunkMsg.fileID + "_" + putChunkMsg.chunkNo + ".cnk");

            fos.write(putChunkMsg.data);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
