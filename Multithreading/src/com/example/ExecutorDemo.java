package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {




    public static void main(String[] args) {


        // for cpu intensive task ideal pool size
        // take this number of threads cause one thread will be handled by one core at a time
        int coreCount = Runtime.getRuntime().availableProcessors();

        // for higher count for IO tasks or normally as well
        ExecutorService service = Executors.newFixedThreadPool(100);


//        submit task for executor
        for (int i = 0; i < 100; i++) {
            service.execute(new Task());
            service.execute(new IOTask());
            service.execute(new CpuIntensiveTask());
        }
        System.out.println("Thread Name: "+ Thread.currentThread().getName());
    }

    static class Task implements Runnable {
        public void run() {
            System.out.println("Thread name: " + Thread.currentThread().getName());
        }
    }

    static class CpuIntensiveTask implements Runnable {
        public void run() {
            System.out.println("Thread name: " + Thread.currentThread().getName());
        }
    }

    static class IOTask implements Runnable {
        public void run() {
            System.out.println("Thread name: " + Thread.currentThread().getName());
        }
    }
}
