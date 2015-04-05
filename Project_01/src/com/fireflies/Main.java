package com.fireflies;

import com.fireflies.reference.Reference;
import com.fireflies.threads.IO;
import com.fireflies.threads.Listener;

import java.io.*;
public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        Reference.setMcAddressPorts(args[0],args[1],args[2],Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]));

        Reference.libraryPath = args[6];

        LibraryHandler.loadLibrary();

        // Launch IO Thread
        IO io = new IO();
        io.start();

        // Launch Listener Thread
        Listener listener = new Listener();
        listener.start();

    }
}
