package com.joaoneto;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class Client {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws IOException {
        // Get the address that we are going to connect to.
        InetAddress address = InetAddress.getByName(args[0]);

        // Create a buffer of bytes, which will be used to store
        // the incoming bytes containing the information from the server.
        // Since the message is small here, 256 bytes should be enough.
        byte[] buf = new byte[256];

        // Create a new Multicast socket (that will allow other sockets/programs
        // to join it as well.

        int port = Integer.parseInt(args[1]);
        MulticastSocket clientSocket = new MulticastSocket(port);
        //Joint the Multicast group.
        clientSocket.joinGroup(address);

        System.out.println("Joined Multicast Group");

        //Receive Server Service Port
        DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
        clientSocket.receive(msgPacket);


        //Ler do buffer IP:porta
        String msg = new String(buf, 0, buf.length);
        String[] receivedArray = msg.split(":");

        InetAddress serviceAddress = InetAddress.getByName(receivedArray[0]);
        int servicePort = Integer.parseInt(receivedArray[1]);

        //Enviar para a porta o pedido (args)
        String requestMessage = "";
        for (int i = 0; i < args.length; i++) {
            requestMessage = requestMessage + args[i];
            if(i < args.length - 1)
                 requestMessage = requestMessage + "|";
        }

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket requestPacket = new DatagramPacket(requestMessage.getBytes(),
                requestMessage.getBytes().length, serviceAddress, servicePort);

        socket.send(requestPacket);

        System.out.println("Sent message " + requestMessage);

        byte[] rbuf = new byte[1024];
        DatagramPacket replyPacket = new DatagramPacket(rbuf,rbuf.length);
        socket.receive(replyPacket);

        System.out.println(replyPacket.toString());

    }
}