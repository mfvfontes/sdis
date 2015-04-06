package com.fireflies;

import com.fireflies.reference.Reference;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by João on 19/03/2015.
 */
public class ChunkHandler {

    static ArrayList<Chunk> getChunks(FileInputStream stream, String fileID)
    {

        ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
        ArrayList<byte[]> datas = new ArrayList<byte[]>();

        try {

            int fileSize = 0;
            int nChunks = 0;
            int bytesRead;

            do
            {
                byte[] buffer = new byte[Reference.chunkSize];

                bytesRead = stream.read(buffer);
                Chunk newChunk = new Chunk(buffer, nChunks, bytesRead);
                chunkList.add(newChunk);
                nChunks++;
                fileSize += bytesRead;

            } while (bytesRead == Reference.chunkSize);


            stream.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chunkList;
    }

    public static void createFile (String fileName, ArrayList<byte[]> chunks)
    {
        try {
            FileOutputStream stream = new FileOutputStream(fileName);

            for (int i = 0; i < chunks.size(); i++)
            {
                stream.write(chunks.get(i));
            }
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
