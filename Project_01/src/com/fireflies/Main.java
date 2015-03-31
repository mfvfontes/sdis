package com.fireflies;

import com.fireflies.network.NetworkHandler;
import com.fireflies.threads.Backup;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Launch Listener Thread
        /*
        Listener listener = new Listener();
        listener.run();
        */

        //
        printOptions();

        String input;
        Scanner scanIn = new Scanner(System.in);

        while(true) {

            input = scanIn.nextLine();

            System.out.println(input);

            File f = new File(input);

            f.print();

            ChunkHandler.createFile("files/out.txt",f.getChunks());

            Backup backup = new Backup(f);
            backup.run();

        }

    }

    private static void printOptions () {
        System.out.println("Enter the file to backup");
    }
}
