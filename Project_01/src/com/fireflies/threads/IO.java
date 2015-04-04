package com.fireflies.threads;

import com.fireflies.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by João on 01/04/2015.
 */
public class IO extends Thread {
    @Override
    public void run() {

        System.out.println("Hello");

        while(true) {
            printOptions();
            String input = null;

            File f;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            f = new File(input);

            Backup backup = new Backup(f);
            backup.run();
        }
    }


    private static void printOptions () {
        System.out.println("Enter the file to backup");
    }
}
