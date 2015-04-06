package com.fireflies.threads;

import com.fireflies.ChunkID;
import com.fireflies.Utils;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.ChunkMsg;
import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;

import javax.rmi.CORBA.Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by João on 04/04/2015.
 */
public class SendChunk extends Thread {
    private Message message;

    public ChunkID chunkID;

    public SendChunk(Message message) {
        this.message = message;
        this.chunkID = new ChunkID(message.fileID,message.chunkNo);

        Utils.sendChunks.add(this);
    }

    @Override
    public void run() {

        //System.out.println("Send Chunk Thread for chunk no " + message.chunkNo + " from file " + message.fileID);

        try {
            FileInputStream chunkStream = new FileInputStream(Reference.chunksFolder + "/" + message.fileID + "_" + message.chunkNo + ".cnk");
            byte [] data = new byte[Reference.chunkSize];
            int bytesRead = chunkStream.read(data);
            byte [] trimmedData =  Arrays.copyOfRange(data,0,bytesRead);
            chunkStream.close();

            ChunkMsg chunkMsg = new ChunkMsg(message.chunkNo, message.fileID, trimmedData);

            Thread.sleep(new Random().nextInt(400));

            NetworkHandler.sendToMDR(chunkMsg);

        } catch (FileNotFoundException e) {
            System.out.println("Chunk not found at " + Reference.chunksFolder + "/" + message.fileID + "_" + message.chunkNo + ".cnk");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Another peer sent the chunk before me");
        }

        Utils.sendChunks.remove(this);
    }
}
