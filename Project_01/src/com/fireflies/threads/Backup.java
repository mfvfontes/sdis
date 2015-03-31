package com.fireflies.threads;

import com.fireflies.Chunk;
import com.fireflies.File;

import java.util.ArrayList;

/**
 * Created by João on 28/03/2015.
 */
public class Backup implements Runnable {

    File file;

    public Backup (File file)
    {
        this.file = file;
    }

    @Override
    public void run() {

        ArrayList<Chunk> chunks = file.getChunks();

        for (int chunk = 0; chunk < chunks.size(); chunk++) {
            /*
            TODO:
            Backup Chunk
             */
        }

    }
}
