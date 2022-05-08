package com.memory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Memory {
    private final int V;
    private final int M;
    private final int S;
    private final int P;
    private Memory(final int v, final int m, final int s, final int p) {
        V = v;
        M = m;
        S = s;
        P = p;
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
            Integer x = null;
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
