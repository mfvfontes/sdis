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

        //System.out.println("Backup Thread for file " + file.getName());

        String previousFileId = LibraryHandler.fileLibrary.getFileID(file.getName());
        if (previousFileId != null)
        {
            Deletion deletion = new Deletion(previousFileId);
            deletion.start();
        }

        LibraryHandler.fileLibrary.addFile(file);

        ArrayList<Chunk> chunks = file.getChunks();

        for (int chunk = 0; chunk < chunks.size(); chunk++) {

            LibraryHandler.fileLibrary.addChunk(new ChunkID(file.getFileID(), chunk), file.getReplication());

            ChunkBackup chunkBackup = new ChunkBackup(chunk,file);
            chunkBackup.start();
        }

        LibraryHandler.saveLibrary();


    }
}
