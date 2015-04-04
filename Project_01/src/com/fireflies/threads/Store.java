package com.fireflies.threads;

import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.Message;
import com.fireflies.network.messages.Stored;
import com.fireflies.reference.Reference;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        saveChunk();

        Stored msg = new Stored(putChunkMsg.chunkNo,putChunkMsg.fileID);

        int timeToWait = new Random().nextInt(400);

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NetworkHandler.sendToMC(msg);

        System.out.println("Waited " + timeToWait + "ms and sent Stored to MC");
    }

    private void saveChunk()
    {
        try {
            FileOutputStream chunkStream = new FileOutputStream(Reference.chunksFolder + putChunkMsg.fileID + "_" + putChunkMsg.chunkNo + ".cnk");
            ObjectOutputStream out = new ObjectOutputStream(chunkStream);
            out.write(putChunkMsg.chunk.getData());
            out.close();
            chunkStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Saved chunk no " + putChunkMsg.chunkNo + " from file " + putChunkMsg.fileID);
    }
}
