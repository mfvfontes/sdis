package com.fireflies;

import com.fireflies.reference.Reference;

import java.io.*;

/**
 * Created by João on 03/04/2015.
 */
public class LibraryHandler {

    public static FileLibrary fileLibrary;

    public static void loadLibrary()
    {
        try {
            FileInputStream libraryFile = new FileInputStream(Reference.libraryPath);
            ObjectInputStream in = new ObjectInputStream(libraryFile);
            fileLibrary = (FileLibrary) in.readObject();
            in.close();
            libraryFile.close();
        } catch (IOException e) {
            System.out.println("Library does not yet exist");
            java.io.File file = new java.io.File("tmp/");
            if(!file.mkdirs())
                System.out.println("Failed mkdirs");
            fileLibrary = new FileLibrary();

            saveLibrary();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void saveLibrary()
    {
        try {
            FileOutputStream libraryFile = new FileOutputStream(Reference.libraryPath);
            ObjectOutputStream out = new ObjectOutputStream(libraryFile);
            out.writeObject(fileLibrary);
            out.close();
            libraryFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printLibrary()
    {
        System.out.println("\nFile Library");
        System.out.println(fileLibrary.chunkReplication);
        System.out.println(fileLibrary.chunks);
    }

}
