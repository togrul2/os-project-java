package com;
import com.memory.Memory;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static Memory memory;

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()) {
                String data = scanner.next();
                switch (data) {
                    case "Init" -> {
                        int v = Integer.parseInt(scanner.next());
                        int m = Integer.parseInt(scanner.next());
                        int s = Integer.parseInt(scanner.next());
                        int p = Integer.parseInt(scanner.next());
                        memory = Memory.init(v, m, s, p);
                        if (memory == null) {
                            System.exit(1);
                        }
                    }
                    case "Read" -> {
                        int a = Integer.parseInt(scanner.next());
                        memory.read(a);
                    }
                    case "Write" -> {
                        int a = Integer.parseInt(scanner.next());
                        int x = Integer.parseInt(scanner.next());
                        memory.write(a, x);
                    }
                    case "Exit" -> {
                        memory.log("Exited");
                        System.exit(0);
                    }
                }
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("An error occurred while opening a file.");
            e.printStackTrace();
        }
    }
}
