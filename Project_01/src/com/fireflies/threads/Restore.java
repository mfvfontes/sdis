package com.fireflies.threads;

import com.fireflies.ChunkHandler;
import com.fireflies.LibraryHandler;

import java.util.ArrayList;

/**
 * Created by João on 04/04/2015.
 */
public class Restore extends Thread {

    private String destinationName;
    private String fileName;

    public static ArrayList<ChunkRestore> chunkRestores;

    public Restore(String fileName, String destinationName)
    {
        this.fileName = fileName;
        this.destinationName = destinationName;
        chunkRestores = new ArrayList<ChunkRestore>();
    }

    @Override
    public void run ()
    {
        //Get the fileId and chunks
        String fileId = LibraryHandler.fileLibrary.getFileID(fileName);
        int nChunks = LibraryHandler.fileLibrary.getNoChunks(fileId);

        //Send messages to the network
        for (int chunk = 0; chunk < nChunks; chunk++) {
            ChunkRestore chunkRestore = new ChunkRestore(fileId,chunk);
            chunkRestores.add(chunkRestore);
            chunkRestore.start();
        }

        ArrayList<byte[]> chunks = new ArrayList<byte[]>();

        //Wait for the chunks
        for (ChunkRestore thread : chunkRestores) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            chunks.add(thread.data);
        }

        ChunkHandler.createFile(destinationName,chunks);
    }
}
