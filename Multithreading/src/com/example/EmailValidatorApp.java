package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class EmailValidatorApp {
    private static final String INPUT_CSV = "src/com/example/resources/users.csv";
    private static final String OUTPUT_CSV = "invalid_emails.csv";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        // we are using synchronised list as we need thread safe behaviour
        List<String> invalidRows = Collections.synchronizedList(new ArrayList<>());


        // reading csv from input file
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_CSV))) {

            String line;
            while ((line = br.readLine()) != null) {
                String row = line;

                executor.submit(() -> {
                    String[] parts = row.split(",");
                    if(parts.length == 3) {
                        String email = parts[2].trim();
                        if (!EMAIL_PATTERN.matcher(email).matches()) {
                            invalidRows.add(email);
                        }
                    }
                });
        }
        } catch (IOException io) {
            System.out.println("Error reading file: " + io.getMessage());
        }

        executor.shutdown();


        // this makes wait for main thread until all tasks submitted to executor
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException IE) {
            System.out.println(IE.getMessage());
        }

        // writing data in output file

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_CSV))) {
            synchronized (invalidRows) {
                for (String invalid : invalidRows) {
                    bw.write(invalid);
                    bw.newLine();
                }
            }
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }

        System.out.println("Validation completed. Invalid emails saved to: "+ OUTPUT_CSV);

    }

}
