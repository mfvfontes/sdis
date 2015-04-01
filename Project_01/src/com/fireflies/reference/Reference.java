package com.fireflies.reference;

import java.io.IOException;
import java.net.MulticastSocket;

/**
 * Created by João on 19/03/2015.
 */
public class Reference {
    public static double version = 0.1;

    // Message Types
    public static String msgPutChunk = "putchunk";


    // Multicast Addresses
    public static String mcAdress = "228.5.6.7";
    public static String mdbAdress = "228.5.6.8";
    public static String mdrAdress = "228.5.6.9";

    // Multicast Ports
    public static int mcPort = 4445;
    public static int mdbPort = 4446;
    public static int mdrPort = 4447;

    // Multicast Sockets
    public static MulticastSocket mcSocket;
    public static MulticastSocket mdbSocket;
    public static MulticastSocket mdrSocket;

    static {
        try {
            mcSocket = new MulticastSocket(mcPort);
            mdbSocket = new MulticastSocket(mdbPort);
            mdrSocket = new MulticastSocket(mdrPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int chunkSize = 64000;
    public static int packetSize = 70000;

    // Hashing
    final public static char[] hexArray = "0123456789ABCDEF".toCharArray();
}
