package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.LibraryHandler;
import com.fireflies.Utils;
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

        //System.out.println("Chunk Received Tread from file " + message.fileID + " chunk no " + message.chunkNo);

        if (LibraryHandler.fileLibrary.haveFile(message.fileID))
        {
            ArrayList<ChunkRestore> chunkRestores = Restore.chunkRestores;

            if (chunkRestores == null)
                return;

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
        } else
        {
            for (SendChunk sendChunk : Utils.sendChunks)
            {
                if (sendChunk.chunkID.equals(new ChunkID(message.fileID, message.chunkNo)))
                {
                    sendChunk.interrupt();
                }
            }
        }
    }
}
