package com.fireflies;

import java.io.Serializable;

/**
 * Created by João on 19/03/2015.
 */
public class Chunk implements Serializable {
    private byte[] data;
    private int size;
    private int chunkNo;
    private String fileID;

    public Chunk(byte[] data, int chunkNo, String fileID) {
        this.data = data;
        this.size = data.length;
        this.chunkNo = chunkNo;
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }
}
