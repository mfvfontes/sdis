package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.Removed;
import com.fireflies.reference.Reference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by João on 05/04/2015.
 */
public class DeleteStoredChunks extends Thread {
    String fileID;

    boolean reclaim = false;

    public DeleteStoredChunks(String fileID)
    {
        this.fileID = fileID;
    }

    public void setReclaim(boolean reclaim) {
        this.reclaim = reclaim;
    }

    @Override
    public void run() {

        //System.out.println("Delete Stored Chunks Thread for file " + fileID);

        HashMap<ChunkID,Integer> chunks = LibraryHandler.fileLibrary.getStoredChunks();

        ArrayList<Integer> chunksToRemove = new ArrayList<Integer>();

        for (ChunkID chunkID : chunks.keySet())
        {

            if (chunkID.fileID.equalsIgnoreCase(fileID))
            {
                chunksToRemove.add(chunkID.chunkNo);

            }
        }

        for (Integer chunkNo : chunksToRemove)
        {
            System.out.println("Deleted chunk no " + chunkNo);
            LibraryHandler.fileLibrary.removeStoredChunk(new ChunkID(fileID,chunkNo));
            deleteChunk(chunkNo);

            if (reclaim) {
                Removed msg = new Removed(fileID, chunkNo);
                for (int i = 0; i < Reference.numberRepeatMessages; i++) {
                    NetworkHandler.sendToMC(msg);
                }
            }

        }


        LibraryHandler.saveLibrary();

    }

    private void deleteChunk(int chunkNo) {
        try {
            Files.delete(Paths.get(Reference.chunksFolder + "/" + fileID + "_" + chunkNo + ".cnk"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
