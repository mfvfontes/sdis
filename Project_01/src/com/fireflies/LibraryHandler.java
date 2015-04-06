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
            FileInputStream libraryFile = new FileInputStream(Reference.libraryFolder + Reference.libraryFileName + ".flib");
            ObjectInputStream in = new ObjectInputStream(libraryFile);
            fileLibrary = (FileLibrary) in.readObject();
            in.close();
            libraryFile.close();
        } catch (IOException e) {
            System.out.println("Library does not yet exist");
            java.io.File file = new java.io.File(Reference.libraryFolder);
            file.mkdirs();
            fileLibrary = new FileLibrary();

            saveLibrary();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        java.io.File file = new java.io.File(Reference.chunksFolder + "/");
        file.mkdirs();
    }

    public static void saveLibrary()
    {
        try {
            FileOutputStream libraryFile = new FileOutputStream(Reference.libraryFolder + Reference.libraryFileName + ".flib");
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

}
