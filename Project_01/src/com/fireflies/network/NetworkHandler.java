package com.fireflies.network;

import com.fireflies.File;
import com.fireflies.network.messages.Message;
import com.fireflies.reference.Reference;
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.io.IOException;
import java.net.*;

/**
 * Created by João on 28/03/2015.
 */
public class NetworkHandler {

    static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void sendToMC(Message message)
    {
        send(Reference.mcSocket,Reference.mcAdress,Reference.mcPort,message.getBytes());
    }

    public static void sendToMDB(Message message)
    {
        send(Reference.mdbSocket,Reference.mdbAdress,Reference.mdbPort,message.getBytes());
    }

    private static void send(MulticastSocket socket, String address, int port, byte[] message) {

        try {
            socket.setLoopbackMode(true);
            InetAddress inetAddress = InetAddress.getByName(address);
            DatagramPacket packet = new DatagramPacket(message,message.length,inetAddress,port);
            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
