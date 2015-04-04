package com.fireflies.reference;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.Ref;

/**
 * Created by João on 19/03/2015.
 */
public class Reference {
    public static double version = 0.1;

    // Message Types
    public static String msgPutChunk = "putchunk";
    public static String msgGetChunk = "getchunk";
    public static String msgDeleteChunk = "delete";
    public static String msgReclaimSpace = "removed";
    public static String msgStored = "stored";


    // Multicast Addresses
    public static String mcAddress = "228.5.6.7";
    public static String mdbAddress = "228.5.6.8";
    public static String mdrAddress = "228.5.6.9";

    // Multicast Ports
    public static int mcPort = 4445;
    public static int mdbPort = 4446;
    public static int mdrPort = 4447;

    public static void setMcAddressPorts(String mcAddress, String mdbAddress, String mdrAddress, int mcPort, int mdbPort, int mdrPort)
    {
        Reference.mcAddress = mcAddress;
        Reference.mdbAddress = mdbAddress;
        Reference.mdrAddress = mdrAddress;

        Reference.mcPort = mcPort;
        Reference.mdbPort = mdbPort;
        Reference.mdrPort = mdrPort;

    }

    // Data sizes
    public static int chunkSize = 64000;
    public static int packetSize = 70000;

    // Hashing
    final public static char[] hexArray = "0123456789ABCDEF".toCharArray();

    // Folders
    public static String libraryPath = "tmp/fileLibrary.flib";
    public static String chunksFolder = "chunks/";

    // Protocol
    public static int maxBackupAttempts = 7;
    public static int listenTimeout = 20;
}
