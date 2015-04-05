package com.fireflies;

import java.io.Serializable;

/**
 * Created by João on 03/04/2015.
 */
public class ChunkID implements Serializable {

    public String fileID;
    public int chunkNo;

    public ChunkID(String fileID, int chunkNo) {
        this.fileID = fileID;
        this.chunkNo = chunkNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkID))
            return false;

        ChunkID other = (ChunkID) obj;
        if (other.fileID.equalsIgnoreCase(fileID)&& other.chunkNo == chunkNo)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.fileID.hashCode());
        hash = 89 * hash + (this.chunkNo);
        return hash;
    }
}
