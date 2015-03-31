package com.fireflies;

/**
 * Created by João on 19/03/2015.
 */
public class Chunk {
    private byte[] data;
    private int size;

    public Chunk(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    @Override
    public String toString() {
        return new String(this.data);
    }


    public byte[] getData() {
        return data;
    }
}
