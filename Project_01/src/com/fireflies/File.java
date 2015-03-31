package com.fireflies;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by João on 25/03/2015.
 */
public class File {
    private String name;
    private Date date;
    private int replication;
    private String fileID;

    private ArrayList<Chunk> chunks;

    public File (String name) {
        this.name = name;
        this.date = new Date();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            String id = name + date.toString();
            md.update(id.getBytes());
            byte[] digest = md.digest();

            fileID = new String(digest);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-256 not found!");
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(this.name);
            this.chunks = ChunkHandler.getChunks(fileInputStream);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void print(){
        System.out.println("File name:\t" + name);
        System.out.println("Insert date:\t" + date);
        System.out.println("File content:");
        for (int i = 0; i < chunks.size(); i++) {
            System.out.println(chunks);
        }
    }

    public ArrayList<Chunk> getChunks() {
        return chunks;
    }
}
