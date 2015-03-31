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
public class Listener implements Runnable{

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
        byte[] buffer = new byte[Reference.chunkSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true)
        {
            try {
                mc.receive(packet);
                /* TODO
                Handle packet
                 */
            } catch (SocketTimeoutException e) {
                System.out.println("MC timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mdb.receive(packet);
                /* TODO
                Handle packet
                 */
            } catch (SocketTimeoutException e) {
                System.out.println("MDB timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mdr.receive(packet);
                /* TODO
                Handle packet
                 */
            } catch (SocketTimeoutException e) {
                System.out.println("MDR timed out on Receive()");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
