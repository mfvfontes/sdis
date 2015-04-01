package com.fireflies;
import com.fireflies.threads.IO;
import com.fireflies.threads.Listener;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Launch IO Thread
        IO io = new IO();
        io.start();

        // Launch Listener Thread
        Listener listener = new Listener();
        listener.start();

    }
}
