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
    public static String msgDelete = "delete";
    public static String msgRemoved = "removed";
    public static String msgStored = "stored";
    public static String msgChunk = "chunk";

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
    public static int packetSize = 65000;
    public static int diskSpace;

    // Hashing
    final public static char[] hexArray = "0123456789ABCDEF".toCharArray();

    // Folders
    public static String libraryFolder = "lib/";
    public static String libraryFileName;
    public static String chunksFolder;

    // Protocol
    public static int maxBackupAttempts = 5;
    public static int listenTimeout = 20;
    public static int numberRepeatMessages = 3;
}
