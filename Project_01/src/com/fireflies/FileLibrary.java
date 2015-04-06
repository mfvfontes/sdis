package com.fireflies;

import com.fireflies.reference.Reference;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by João on 03/04/2015.
 */
public class FileLibrary implements java.io.Serializable {


    public FileLibrary() {
        actualOwnChunkReplication = new HashMap<ChunkID, Integer>();
        desiredOwnChunkReplication = new HashMap<ChunkID, Integer>();
        fileNameToID = new HashMap<String, String>();
        nChunks = new HashMap<String, Integer>();
        storedOwnLocations = new HashMap<ChunkID, HashSet<InetSocketAddress>>();

        storedChunksSize = new HashMap<ChunkID, Integer>();
        actualStoredReplication = new HashMap<ChunkID, Integer>();
        desiredStoredReplication = new HashMap<ChunkID, Integer>();
        availableSpace = Reference.diskSpace;
        storedRemoteLocations = new HashMap<ChunkID, HashSet<InetSocketAddress>>();
    }

    // Own files
    HashMap<String,String> fileNameToID;
    HashMap<String,Integer> nChunks;
    HashMap<ChunkID,Integer> actualOwnChunkReplication;
    HashMap<ChunkID,Integer> desiredOwnChunkReplication;
    HashMap<ChunkID,HashSet<InetSocketAddress>> storedOwnLocations;

    public void removeFile(String fileID)
    {

        //Get fileName
        String fileName = null;

        for (Map.Entry<String,String> entry : fileNameToID.entrySet())
        {
            if(fileID.equals(entry.getValue()))
            {
                fileName = entry.getKey();
            }
        }

        if (fileName != null)
        {
            fileNameToID.remove(fileName);

            for (int i = 0; i < getNoChunks(fileID); i++) {
                ChunkID chunkID = new ChunkID(fileID,i);
                actualOwnChunkReplication.remove(chunkID);
                desiredOwnChunkReplication.remove(chunkID);
                storedOwnLocations.remove(chunkID);
            }

            nChunks.remove(fileID);
        }


    }

    public String getFileID (String fileName) {
        return fileNameToID.get(fileName);
    }

    public int getNoChunks (String fileID) {
        return nChunks.get(fileID);
    }

    public boolean haveFile (String fileID)
    {
        return fileNameToID.containsValue(fileID);
    }

    public void addFile (File file)
    {

        String previousFileId = getFileID(file.getName());
        if (previousFileId != null)
        {
            int numberChunks = getNoChunks(previousFileId);

            for (int i = 0; i < numberChunks; i++) {
                ChunkID chunkID = new ChunkID(previousFileId,i);
                actualOwnChunkReplication.remove(chunkID);
                desiredOwnChunkReplication.remove(chunkID);
            }

            nChunks.remove(previousFileId);

        }

        fileNameToID.put(file.getName(),file.getFileID());
        nChunks.put(file.getFileID(), file.getChunks().size());
    }

    public void addChunk (ChunkID id, int desiredReplication)
    {
        actualOwnChunkReplication.put(id, 0);
        desiredOwnChunkReplication.put(id,desiredReplication);
        storedOwnLocations.put(id, new HashSet<InetSocketAddress>());
    }

    public Integer getChunkReplication (ChunkID id)
    {
        return actualOwnChunkReplication.get(id);
    }

    public boolean incChunkReplication (ChunkID id)
    {
        Integer previousReplication;
        previousReplication = actualOwnChunkReplication.get(id);

        if (previousReplication != null)
        {
            actualOwnChunkReplication.put(id, previousReplication + 1);
            return true;
        }

        previousReplication = actualStoredReplication.get(id);

        if (previousReplication != null)
        {
            actualStoredReplication.put(id, previousReplication + 1);
            return true;
        }

        return false;

    }

    public boolean decChunkReplication (ChunkID id)
    {
        Integer previousReplication;
        previousReplication = actualOwnChunkReplication.get(id);

        if (previousReplication != null)
        {
            actualOwnChunkReplication.put(id, previousReplication - 1);
            return true;
        }

        previousReplication = actualStoredReplication.get(id);

        if (previousReplication != null)
        {
            actualStoredReplication.put(id, previousReplication - 1);
            return true;
        }

        return false;
    }

    public void addStoredPeer (ChunkID id, InetSocketAddress address)
    {
        if (haveFile(id.fileID)) {
            HashSet<InetSocketAddress> list = storedOwnLocations.get(id);
            list.add(address);
            storedOwnLocations.put(id, list);
        }
    }

    public void removeStoredPeer(ChunkID id, InetSocketAddress address) {
        if (haveFile(id.fileID)) {
            HashSet<InetSocketAddress> list = storedOwnLocations.get(id);
            list.remove(address);
            storedOwnLocations.put(id, list);
        }
    }

    public boolean peerAlreadyHadChunk (ChunkID id, InetSocketAddress address)
    {
        return storedOwnLocations.get(id).contains(address);
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

    HashMap<ChunkID,Integer> storedChunksSize;
    HashMap<ChunkID,Integer> actualStoredReplication;
    HashMap<ChunkID,Integer> desiredStoredReplication;
    public int availableSpace;
    HashMap<ChunkID,HashSet<InetSocketAddress>> storedRemoteLocations;

    public int getStoredActualReplication (ChunkID id)
    {
        return actualStoredReplication.get(id);
    }

    public int getStoredDesiredReplication (ChunkID id)
    {
        return desiredStoredReplication.get(id);
    }

    public boolean haveStoredChunk(ChunkID chunkID)
    {
        return storedChunksSize.containsKey(chunkID);
    }

    public void addStoredChunk(ChunkID chunkID, int size, int replication)
    {
        storedChunksSize.put(chunkID, size);
        actualStoredReplication.put(chunkID, 1);
        desiredStoredReplication.put(chunkID, replication);
        storedRemoteLocations.put(chunkID, new HashSet<InetSocketAddress>());

        availableSpace -= size;
    }

    public HashMap<ChunkID,Integer> getStoredChunks()
    {
        return storedChunksSize;
    }

    public void removeStoredChunk(ChunkID chunkID)
    {
        int size = storedChunksSize.get(chunkID);
        System.out.println("Removed Chunk Size = " + size);
        availableSpace += size;
        storedChunksSize.remove(chunkID);
        actualStoredReplication.remove(chunkID);
        desiredStoredReplication.remove(chunkID);
    }

    public void incStoredChunkReplication (ChunkID id, InetSocketAddress address)
    {
        if (!peerAlreadyHadRemoteChunk(id,address))
        {
            HashSet<InetSocketAddress> list = storedRemoteLocations.get(id);
            list.add(address);
            storedRemoteLocations.put(id, list);
            int previousReplication = actualStoredReplication.get(id);
            actualStoredReplication.put(id,previousReplication+1);
        }
    }

    public void decStoredChunkReplication (ChunkID id, InetSocketAddress address)
    {
        if (peerAlreadyHadRemoteChunk(id,address))
        {
            HashSet<InetSocketAddress> list = storedRemoteLocations.get(id);
            list.remove(address);
            storedRemoteLocations.put(id, list);
            int previousReplication = actualStoredReplication.get(id);
            actualStoredReplication.put(id,previousReplication-1);
        }
    }

    public boolean peerAlreadyHadRemoteChunk (ChunkID id, InetSocketAddress address)
    {
        return storedRemoteLocations.get(id).contains(address);
    }

    public void print()
    {
        System.out.println("File Library (" + Reference.libraryFileName + ".flib)");
        System.out.println("\nOwn Files");
        for (String fileName : fileNameToID.keySet())
        {
            String fileID = fileNameToID.get(fileName);
            System.out.println(fileName);
            for (int chunk = 0; chunk < nChunks.get(fileID); chunk++) {
                ChunkID chunkID = new ChunkID(fileID,chunk);
                int actualReplication = actualOwnChunkReplication.get(chunkID);
                int desiredReplication = desiredOwnChunkReplication.get(chunkID);
                System.out.println("\t" + chunk + "\t" + actualReplication + " " + desiredReplication);
            }
        }
        System.out.println("\nStored Chunks");
        for (ChunkID chunkID : storedChunksSize.keySet())
        {
            String fileID = chunkID.fileID;
            int chunkNo = chunkID.chunkNo;
            int size = storedChunksSize.get(chunkID);
            int actualReplication = actualStoredReplication.get(chunkID);
            int desiredReplication = desiredStoredReplication.get(chunkID);

            System.out.println("\t" + fileID + " " + chunkNo + "\t" + size + "\t" + actualReplication + " " + desiredReplication);
        }
        System.out.println("Using " + (Reference.diskSpace - availableSpace) + " bytes out of " + Reference.diskSpace + " bytes available. " + (availableSpace/(double)Reference.diskSpace)*100 + "% available." );
    }

}
