package com.fireflies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by João on 03/04/2015.
 */
public class FileLibrary implements java.io.Serializable {

    public FileLibrary() {
        chunkReplication = new HashMap<ChunkID, Integer>();
        chunks = new ArrayList<ChunkID>();
        fileNameToID = new HashMap<String, String>();
        nChunks = new HashMap<String, Integer>();
    }

    // Own files
    HashMap<String,String> fileNameToID;
    HashMap<String,Integer> nChunks;
    HashMap<ChunkID,Integer> chunkReplication;

    public String getFileID (String fileName) { return fileNameToID.get(fileName);}

    public int getNoChunks (String fileID) { return nChunks.get(fileID); }

    public boolean haveFile (String fileID)
    {
        return fileNameToID.containsValue(fileID);
    }

    public void addFile (File file)
    {
        fileNameToID.put(file.getName(),file.getFileID());
        nChunks.put(file.getFileID(), file.getChunks().size());
    }

    public void addChunk (ChunkID id)
    {
        chunkReplication.put(id,0);
    }

    public Integer getChunkReplication (ChunkID id)
    {
        return chunkReplication.get(id);
    }

    public boolean incChunkReplication (ChunkID id)
    {
        int previous;
        previous = chunkReplication.get(id);

        if (previous == -1)
        {
            System.out.println("ChunkID not present in Library");
            return false;
        }

        chunkReplication.put(id, previous + 1);
        return true;
    }

    public boolean decChunkReplication (ChunkID id)
    {
        int previous;
        previous = chunkReplication.get(id);

        if (previous == -1)
        {
            System.out.println("ChunkID not present in Library");
            return false;
        }

        chunkReplication.put(id, previous - 1);
        return true;
    }

    public void printOwnFiles() {

        int fileNo = 1;
        for (Map.Entry<String,String> entry : fileNameToID.entrySet())
        {
            String fileName = entry.getKey();
            System.out.println(fileNo + " - " + fileName);
            fileNo ++;
        }
    }
    // Stored Chunks

    ArrayList<ChunkID> chunks;


    public void addStoredChunk(ChunkID chunkID, Chunk chunk)
    {
        chunks.add(chunkID);

    }

}
