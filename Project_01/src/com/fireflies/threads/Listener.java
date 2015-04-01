package com.fireflies.threads;

import com.fireflies.reference.Reference;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

/**
 * Created by João on 19/03/2015.
 */
public class Listener extends Thread{

    private MulticastSocket mc;
    private MulticastSocket mdb;
    private MulticastSocket mdr;


    @Override
    public void run() {
        try {
            /* Control Multicast */
            mc = new MulticastSocket(Reference.mcPort);
            mc.joinGroup(InetAddress.getByName(Reference.mcAdress));
            mc.setSoTimeout(10);
            Reference.mcSocket.setSoTimeout(10);

            /* Backup Multicast */
            mdb = new MulticastSocket(Reference.mdbPort);
            mdb.joinGroup(InetAddress.getByName(Reference.mdbAdress));
            mdb.setSoTimeout(10);

            /* Restore Multicast */
            mdr = new MulticastSocket(Reference.mdrPort);
            mdr.joinGroup(InetAddress.getByName(Reference.mdrAdress));
            mdr.setSoTimeout(10);

            listen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen()
    {
        byte[] buffer = new byte[Reference.packetSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true)
        {
            try {
                Reference.mcSocket.receive(packet);
                /* TODO
                Handle packet
                 */

                System.out.println("\n\nReceived packet in MC\n\n");
                System.out.println(new String(packet.getData()));

            } catch (SocketTimeoutException e) {
                //System.out.println("MC timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mdb.receive(packet);
                /* TODO
                Handle packet
                 */
            } catch (SocketTimeoutException e) {
                //System.out.println("MDB timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mdr.receive(packet);
                /* TODO
                Handle packet
                 */
            } catch (SocketTimeoutException e) {
                //System.out.println("MDR timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
