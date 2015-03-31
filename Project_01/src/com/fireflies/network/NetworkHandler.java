package com.fireflies.network;

import com.fireflies.network.messages.Message;

import java.net.DatagramSocket;
import java.net.SocketException;

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

    static boolean sendToMC(Message message)
    {

        return false;
    }
}
