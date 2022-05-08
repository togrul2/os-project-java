package com.memory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Memory {
    private final int V;
    private final int M;
    private final int S;
    private final int P;
    private int offsetSize;
    private int[] virtualMemory;
    private int[] pageTable;
    private int[] memory;

    private Memory(final int v, final int m, final int s, final int p) {
        this.V = v;
        this.M = m;
        this.S = s;
        this.P = p;
        this.offsetSize = (int)(Math.log(this.P * 1024) / Math.log(2));
    }

    public static Memory init(final int v, final int m, final int s, final int p) {
        Memory memory = null;
        try {
            memory = new Memory(v, m, s, p);
            memory.log("Initialized");
        } catch (IOException e) {
            System.err.println("Error occurred while opening log file");
            e.printStackTrace();
        }
        return memory;
    }

    public void read(final int a) {
        try {
            int x = 0;
            this.log(String.format("Value at address %d is %d", a, x));
        } catch (IOException e) {
            System.err.println("Error occurred while opening log file");
            e.printStackTrace();
        }
    }

    public void write(final int a, final int x) {
        try {
            this.log(String.format("Written %d to address %d", x, a));
        } catch (IOException e) {
            System.err.println("Error occurred while opening log file");
            e.printStackTrace();
        }
    }

    public void log(String message) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("log.txt", true), true);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        String result = String.format("%s -> %s\n", dateFormat.format(now), message);
        System.out.print(result);
        out.write(result);
        out.close();
    }
}
