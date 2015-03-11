package com.joaoneto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by João on 04/03/2015.
 */
public class ServerBroadcast implements Runnable {

    InetAddress address;
    InetAddress serviceAddress;
    int port;
    int servicePort;

    ServerBroadcast(InetAddress address, int port, int servicePort, InetAddress serviceAddress){
        this.address = address;
        this.serviceAddress = serviceAddress;
        this.port = port;
        this.servicePort = servicePort;
    }

    @Override
    public void run() {
        // Open a new DatagramSocket, which will be used to send the data.
        try {
            DatagramSocket serverSocket = new DatagramSocket();
            while (true) {
                String msg = serviceAddress.toString() + ":" + Integer.toString(servicePort);
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, address, port);
                serverSocket.send(msgPacket);

                System.out.println("Broadcasted " + msg);

                Thread.sleep(1000);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
