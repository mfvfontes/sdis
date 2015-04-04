package com.fireflies;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by João on 03/04/2015.
 */
public class FileLibrary implements java.io.Serializable {

    public FileLibrary() {
        chunkReplication = new HashMap<ChunkID, Integer>();
        chunks = new ArrayList<ChunkID>();
    }

    // Own files

     HashMap<ChunkID,Integer> chunkReplication;

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
        int previous = -1;
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
        int previous = -1;
        previous = chunkReplication.get(id);

        if (previous == -1)
        {
            System.out.println("ChunkID not present in Library");
            return false;
        }

        chunkReplication.put(id, previous - 1);
        return true;
    }

    // Stored Chunks

    ArrayList<ChunkID> chunks;


    public void addStoredChunk(ChunkID chunkID, Chunk chunk)
    {
        chunks.add(chunkID);

    }

}
