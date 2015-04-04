package com.fireflies.threads;

import com.fireflies.Chunk;
import com.fireflies.ChunkID;
import com.fireflies.File;
import com.fireflies.LibraryHandler;
import java.util.ArrayList;

/**
 * Created by João on 28/03/2015.
 */
public class Backup extends Thread {

    File file;

    public Backup (File file)
    {
        this.file = file;
    }

    @Override
    public void run() {


        ArrayList<Chunk> chunks = file.getChunks();

        LibraryHandler.printLibrary();

        for (int chunk = 0; chunk < chunks.size(); chunk++) {

            LibraryHandler.fileLibrary.addChunk(new ChunkID(file.getFileID(), chunk));


            ChunkBackup chunkBackup = new ChunkBackup(chunk,file);
            chunkBackup.start();
        }


        LibraryHandler.printLibrary();

        LibraryHandler.saveLibrary();


    }
}
