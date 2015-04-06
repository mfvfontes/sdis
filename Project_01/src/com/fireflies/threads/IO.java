package com.fireflies.threads;

import com.fireflies.File;
import com.fireflies.LibraryHandler;
import com.fireflies.reference.Reference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by João on 01/04/2015.
 */
public class IO extends Thread {
    @Override
    public void run() {

        System.out.println("Hello");

        while(true) {
            printOptions();
            String input = null;

            File f;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = br.readLine();
                String[] array = input.split(" ");

                if (array[0].equalsIgnoreCase("backup"))
                {
                    f = new File(array[1],Integer.parseInt(array[2]));
                    Backup backup = new Backup(f);
                    backup.run();
                }
                else if (array[0].equalsIgnoreCase("list"))
                {
                    System.out.println("List of owned files");
                    LibraryHandler.fileLibrary.printOwnFiles();
                }
                else if (array[0].equalsIgnoreCase("restore"))
                {
                    Restore restore = new Restore(array[1],array[2]);
                    restore.start();
                }
                else if (array[0].equalsIgnoreCase("delete"))
                {
                    Deletion deletion = new Deletion(LibraryHandler.fileLibrary.getFileID(array[1]));
                    deletion.start();
                }
                else if (array[0].equalsIgnoreCase("library"))
                    LibraryHandler.fileLibrary.print();
                else if (array[0].equalsIgnoreCase("reclaim"))
                {
                    SpaceReclaiming spaceReclaiming = new SpaceReclaiming(Integer.parseInt(array[1]));
                    spaceReclaiming.start();
                }
                else if (array[0].equalsIgnoreCase("grant"))
                {
                    Reference.diskSpace += Integer.parseInt(array[1]);
                    LibraryHandler.fileLibrary.availableSpace += Integer.parseInt(array[1]);
                }

                else
                {
                    System.out.println(array[0] + " is not a valid command");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void printOptions () {
        System.out.println("Enter your commands");
    }
}
