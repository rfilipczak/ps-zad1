package edu.p.lodz.pl;

public class Program1 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Hello world!"));

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}