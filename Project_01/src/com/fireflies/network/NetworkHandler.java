package com.fireflies.network;

import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;

import java.io.IOException;
import java.net.*;

/**
 * Created by João on 28/03/2015.
 */
public class NetworkHandler {

    // Multicast Sockets
    public static MulticastSocket mcSocket;
    public static MulticastSocket mdbSocket;
    public static MulticastSocket mdrSocket;

    public static MulticastSocket senderSocket;

    static {
        try {
            // Sender
            senderSocket = new MulticastSocket();

            // MC
            mcSocket = new MulticastSocket(Reference.mcPort);
            mcSocket.joinGroup(InetAddress.getByName(Reference.mcAddress));
            mcSocket.setSoTimeout(Reference.listenTimeout);

            // MDB
            mdbSocket = new MulticastSocket(Reference.mdbPort);
            mdbSocket.joinGroup(InetAddress.getByName(Reference.mdbAddress));
            mdbSocket.setSoTimeout(Reference.listenTimeout);

            // MDR
            mdrSocket = new MulticastSocket(Reference.mdrPort);
            mdrSocket.joinGroup(InetAddress.getByName(Reference.mdrAddress));
            mdrSocket.setSoTimeout(Reference.listenTimeout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToMC(Message message)
    {
        send(mcSocket, Reference.mcAddress,Reference.mcPort,message.getBytes());
    }

    public static void sendToMDB(Message message)
    {
        send(mdbSocket, Reference.mdbAddress,Reference.mdbPort,message.getBytes());
    }

    public static void sendToMDR(Message message)
    {
        send(mdrSocket, Reference.mdrAddress,Reference.mdrPort,message.getBytes());
    }

    private static void send(MulticastSocket socket, String address, int port, byte[] message) {

        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            DatagramPacket packet = new DatagramPacket(message,message.length,inetAddress,port);
            senderSocket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}