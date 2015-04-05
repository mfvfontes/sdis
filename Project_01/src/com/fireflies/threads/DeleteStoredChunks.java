package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.FileLibrary;
import com.fireflies.LibraryHandler;
import com.fireflies.reference.Reference;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by João on 05/04/2015.
 */
public class DeleteStoredChunks extends Thread {
    String fileID;

    public DeleteStoredChunks(String fileID)
    {
        this.fileID = fileID;
    }

    @Override
    public void run() {

        ArrayList<ChunkID> chunks = LibraryHandler.fileLibrary.getStoredChunks();

        ArrayList<Integer> chunksToRemove = new ArrayList<Integer>();

        for (ChunkID chunkID : chunks)
        {

            if (chunkID.fileID.equalsIgnoreCase(fileID))
            {
                chunksToRemove.add(chunkID.chunkNo);

            } else {
                System.out.println("Found chunk from different file");
            }
        }

        for (Integer chunkNo : chunksToRemove)
        {
            System.out.println("Deleted chunk no " + chunkNo);
            LibraryHandler.fileLibrary.removeStoredChunk(new ChunkID(fileID,chunkNo));
            deleteChunk(chunkNo);
        }

    }

    private void deleteChunk(int chunkNo) {
        try {
            Files.delete(Paths.get(Reference.chunksFolder + fileID + "_" + chunkNo + ".cnk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
