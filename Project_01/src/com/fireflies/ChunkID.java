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
}
