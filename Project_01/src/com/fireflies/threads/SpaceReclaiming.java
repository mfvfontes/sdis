package com.fireflies.threads;

import com.fireflies.Chunk;
import com.fireflies.ChunkID;
import com.fireflies.LibraryHandler;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.PutChunk;
import com.fireflies.network.messages.Removed;
import com.fireflies.reference.Reference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by João on 06/04/2015.
 */
public class SpaceReclaiming extends Thread {

    int spaceToReclaim;

    public SpaceReclaiming (int spaceToReclaim)
    {
        this.spaceToReclaim = spaceToReclaim;
    }

    @Override
    public void run() {

        //System.out.println("Space Reclaiming Thread for " + spaceToReclaim + " bytes");

        if (spaceToReclaim > Reference.diskSpace)
        {
            System.out.println("Not possible to reclaim that much disk space");
            return;
        }

        // Check if there is enough space left
        if (LibraryHandler.fileLibrary.availableSpace >= spaceToReclaim)
        {
            updateSpace();
            return;
        }

        boolean deleted;

        do {
            if (LibraryHandler.fileLibrary.getStoredChunks().size() == 0) {
                System.out.println("No more files to delete");
                break;
            }

            deleted = false;

            // If there isn't space check if there are chunks with actual replication > desired replication
            for (ChunkID chunkID : LibraryHandler.fileLibrary.getStoredChunks().keySet())
            {
                if (LibraryHandler.fileLibrary.getStoredActualReplication(chunkID) > LibraryHandler.fileLibrary.getStoredDesiredReplication(chunkID))
                {
                    // If there are delete those
                    deleteChunk(chunkID);

                    deleted = true;


                    break;
                }
            }

            if (!deleted)
            {
                // If there aren't choose one randomly

                ArrayList<ChunkID> ids = new ArrayList<ChunkID>(LibraryHandler.fileLibrary.getStoredChunks().keySet());
                Random rand = new Random();
                ChunkID id = ids.get(rand.nextInt(ids.size()));

                // Send putchunk with the chunk
                FileInputStream chunkStream;

                try {
                    chunkStream = new FileInputStream(Reference.chunksFolder + "/" + id.fileID + "_" + id.chunkNo + ".cnk");
                    byte [] data = new byte[Reference.chunkSize];
                    int bytesRead = 0;
                    bytesRead = chunkStream.read(data);
                    byte [] trimmedData =  Arrays.copyOfRange(data, 0, bytesRead);
                    chunkStream.close();

                    PutChunk msg = new PutChunk(id,trimmedData,LibraryHandler.fileLibrary.getStoredDesiredReplication(id));

                    int nAttempts = 0;
                    int waitTime = 500;
                    int desiredReplication = LibraryHandler.fileLibrary.getStoredDesiredReplication(id);
                    int actualReplication;

                    do {
                        NetworkHandler.sendToMDB(msg);

                        try {
                            Thread.sleep(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        nAttempts++;
                        waitTime *= 2;

                        actualReplication = LibraryHandler.fileLibrary.getStoredActualReplication(id);

                    } while (
                            (actualReplication < desiredReplication )
                            &&
                            (nAttempts < Reference.maxBackupAttempts)
                            );
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Delete the chunk
                deleteChunk(id);
            }
        } while (LibraryHandler.fileLibrary.availableSpace < spaceToReclaim);

        updateSpace();

        LibraryHandler.saveLibrary();
    }

    private void updateSpace() {
        Reference.diskSpace -= spaceToReclaim;
        LibraryHandler.fileLibrary.availableSpace -= spaceToReclaim;
    }


    private void deleteChunk(ChunkID id) {
        LibraryHandler.fileLibrary.removeStoredChunk(id);
        try {
            Files.delete(Paths.get(Reference.chunksFolder + "/" + id.fileID + "_" + id.chunkNo + ".cnk"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Removed msg = new Removed(id.fileID, id.chunkNo);
        for (int i = 0; i < Reference.numberRepeatMessages; i++) {
            NetworkHandler.sendToMC(msg);
        }
    }
}
