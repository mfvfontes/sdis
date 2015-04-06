package com.fireflies;

import com.fireflies.reference.Reference;
import com.fireflies.threads.IO;
import com.fireflies.threads.Listener;

import java.io.*;
public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        // Program invocation : java Main <mcAddress> <mdbAddress> <mdrAddress> <mcPort> <mdbPort> <mdrPort> <libraryFileName> <chunksDir> <diskSpace(kB)>

        if (args.length != 9)
        {
            System.out.println("Program invocation : java Main <mcAddress> <mdbAddress> <mdrAddress> <mcPort> <mdbPort> <mdrPort> <libraryFileName> <chunksDir> <diskSpace(kB)>\n");
            return;
        }

        Reference.setMcAddressPorts(args[0],args[1],args[2],Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]));

        Reference.libraryFileName = args[6];
        Reference.chunksFolder = args[7];
        Reference.diskSpace = Integer.parseInt(args[8])*1000;

        LibraryHandler.loadLibrary();

        // Launch IO Thread
        IO io = new IO();
        io.start();

        // Launch Listener Thread
        Listener listener = new Listener();
        listener.start();

    }
}
