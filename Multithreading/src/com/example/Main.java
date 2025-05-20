package com.example;

public class Main {
    public static void main(String[] args)  {


        Thread thread = new Thread(()->{
            for(int i=0;i<5;i++){
                System.out.println("Hello from 0 "+Thread.currentThread());
            }
        });

        Thread thread1 = new Thread(()->{
           for(int i=0;i<6;i++){
               System.out.println("Hello from  1" + Thread.currentThread());
           }
        });


        try {
            thread.start();

            thread1.start();

            thread1.wait(100);



        }catch(InterruptedException i){
            System.out.println(i.getMessage());
        }

    }
}
