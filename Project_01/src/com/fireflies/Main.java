package com.fireflies;

import com.fireflies.threads.Listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        }

    }

    private static void printOptions () {
        System.out.println("Enter the file to backup");
    }
}
