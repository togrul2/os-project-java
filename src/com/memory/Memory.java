package com.memory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final int V;
    private final int M;
    private final int S;
    private final int P;
    private int offsetSize;
    private int[] virtualMemory;
    private final Map<Integer, Integer> pageTable;
    private int[] physicalMemory;

    private Memory(final int v, final int m, final int s, final int p) {
        this.V = v * 1024;
        this.M = m * 1024;
        this.S = s * 1024;
        this.P = p * 1024;
        this.virtualMemory = new int[(int) (this.V / 4)];
        this.physicalMemory = new int[(int) (this.M / 4)];
        this.pageTable = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            pageTable.put((i * this.P), ((i + 1) * this.P));
        }
        this.offsetSize = (int) (Math.log(this.P) / Math.log(2));
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
            int offset = (int) (a % Math.pow(2, this.offsetSize));
            int vpn = (int) (a / Math.pow(2, this.offsetSize));
            Integer ppn = pageTable.get(vpn);
            if (ppn == null) {
                // Page fault
                this.log(String.format("Page fault at %d", a));
            } else {
                x = physicalMemory[ppn << this.offsetSize + offset];
            }
            this.log(String.format("Value at address %d is %d", a, x));
        } catch (IOException e) {
            System.err.println("Error occurred while opening log file");
            e.printStackTrace();
        }
    }

    public void write(final int a, final int x) {
        try {
            int offset = (int) (a % Math.pow(2, this.offsetSize));
            int vpn = (int) (a / Math.pow(2, this.offsetSize));
            //TODO some way to store vpn value
            this.pageTable.put(vpn, 2);
            Integer ppn = pageTable.get(vpn);
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
