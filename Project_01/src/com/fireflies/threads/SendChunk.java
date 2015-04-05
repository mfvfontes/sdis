package com.fireflies.threads;

import com.fireflies.Chunk;
import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.ChunkMsg;
import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by João on 04/04/2015.
 */
public class SendChunk extends Thread {
    private Message message;

    public SendChunk(Message message) {
        this.message = message;
    }

    @Override
    public void run() {

        try {
            FileInputStream chunkStream = new FileInputStream(Reference.chunksFolder + message.fileID + "_" + message.chunkNo + ".cnk");
            byte [] data = new byte[Reference.chunkSize];
            int bytesRead = chunkStream.read(data);
            byte [] trimmedData =  Arrays.copyOfRange(data,0,bytesRead);
            chunkStream.close();

            ChunkMsg chunkMsg = new ChunkMsg(message.chunkNo, message.fileID, trimmedData);
            NetworkHandler.sendToMDR(chunkMsg);

        } catch (FileNotFoundException e) {
            System.out.println("Chunk not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
