package com.fireflies.threads;

import com.fireflies.LibraryHandler;
import com.fireflies.network.messages.Message;

import java.util.ArrayList;

/**
 * Created by João on 04/04/2015.
 */
public class ChunkReceived extends Thread {
    private Message message;

    public ChunkReceived (Message message)
    {
        this.message = message;
    }

    @Override
    public void run() {
        if (LibraryHandler.fileLibrary.haveFile(message.fileID))
        {
            ArrayList<ChunkRestore> chunkRestores = Restore.chunkRestores;

            for (ChunkRestore restore : chunkRestores)
            {
                if (restore.fileId.equalsIgnoreCase(message.fileID) && restore.chunkNo == message.chunkNo)
                {
                    synchronized (restore.lock) {
                        restore.data = message.data;
                        restore.lock.notify();
                    }
                }
            }
        }
    }
}
