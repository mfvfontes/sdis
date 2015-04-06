package com.fireflies;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by João on 19/03/2015.
 */
public class Chunk implements Serializable {
    private byte[] data;
    private int chunkNo;

    public Chunk(byte[] data, int chunkNo, int bytesRead) {
        this.data = Arrays.copyOfRange(data,0,bytesRead);
        this.chunkNo = chunkNo;
    }

    public byte[] getData() {
        return data;
    }

    public int getChunkNo() {
        return chunkNo;
    }
}
