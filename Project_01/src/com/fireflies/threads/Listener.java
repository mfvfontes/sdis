package com.fireflies.threads;

import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/**
 * Created by João on 19/03/2015.
 */
public class Listener extends Thread{

    @Override
    public void run() {
        listen();
    }

    private void listen()
    {

        while (true)
        {
            listenOnSocket(NetworkHandler.mcSocket);
            listenOnSocket(NetworkHandler.mdbSocket);
            listenOnSocket(NetworkHandler.mdrSocket);
        }
    }

    private void listenOnSocket (MulticastSocket socket)
    {
        byte[] buffer = new byte[Reference.packetSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {
            socket.receive(packet);

            if (packet.getPort() != NetworkHandler.senderSocket.getLocalPort()) {
                handlePacket(packet);
            }

        } catch (SocketTimeoutException e) {
            //Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(DatagramPacket packet)
    {
        byte[] data = Arrays.copyOfRange(packet.getData(),0,packet.getLength());

        Message message = new Message(data);

        if (message.msgType.equalsIgnoreCase(Reference.msgPutChunk))
        {
            Store store = new Store(message);
            store.start();
        } else if (message.msgType.equalsIgnoreCase(Reference.msgStored))
        {
            RemoteStored remoteStored = new RemoteStored(message);
            remoteStored.start();
        } else if (message.msgType.equalsIgnoreCase(Reference.msgChunk))
        {
            ChunkReceived chunkReceived = new ChunkReceived(message);
            chunkReceived.start();
        } else if (message.msgType.equalsIgnoreCase(Reference.msgGetChunk))
        {
            SendChunk sendChunk = new SendChunk(message);
            sendChunk.start();
        } else if (message.msgType.equalsIgnoreCase(Reference.msgDelete))
        {
            DeleteStoredChunks deleteStoredChunks = new DeleteStoredChunks(message.fileID);
            deleteStoredChunks.start();
        }
    }
}
