package com.fireflies.threads;

import com.fireflies.network.NetworkHandler;
import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

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
        byte[] buffer = new byte[Reference.packetSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true)
        {
            listenOnSocket(NetworkHandler.mcSocket,packet);
            listenOnSocket(NetworkHandler.mdbSocket,packet);
            listenOnSocket(NetworkHandler.mdrSocket,packet);
        }
    }

    private void listenOnSocket (MulticastSocket socket, DatagramPacket packet)
    {
        try {
            socket.receive(packet);

            if (packet.getPort() != NetworkHandler.senderSocket.getLocalPort())
                System.out.println("Received something from someone else!");
            //handlePacket(packet);

        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePacket(DatagramPacket packet)
    {
        Message message = new Message(packet.getData());

        if (message.msgType.equalsIgnoreCase(Reference.msgPutChunk))
        {
            Store store = new Store(message);
            store.start();
        }
    }
}
