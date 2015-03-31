package com.fireflies;

import com.fireflies.reference.Reference;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by João on 19/03/2015.
 */
public class ChunkHandler {

    static ArrayList<Chunk> getChunks(FileInputStream stream)
    {

        ArrayList<Chunk> chunkList = new ArrayList<Chunk>();

        try {

            int fileSize = 0;
            int nChunks = 0;
            int bytesRead = 0;

            do
            {
                byte[] buffer = new byte[Reference.chunkSize];

                bytesRead = stream.read(buffer);
                nChunks++;
                fileSize += bytesRead;

                chunkList.add(new Chunk(buffer));

            } while (bytesRead == Reference.chunkSize);

            System.out.println("Read file with " + nChunks + " chunks and a size of " + fileSize + " bytes.");

            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chunkList;
    }

    static void createFile (String fileName, ArrayList<Chunk> chunks)
    {
        try {
            FileOutputStream stream = new FileOutputStream(fileName);

            for (int i = 0; i < chunks.size(); i++)
            {
                stream.write(chunks.get(i).getData());
            }
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
