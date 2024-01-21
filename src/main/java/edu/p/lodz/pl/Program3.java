package edu.p.lodz.pl;

import java.util.ArrayList;

public class Program3 {

    private static class Runner implements Runnable {
        private final static String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        private final static Object mutex = new Object();

        private final int id;

        public Runner(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int index = 0;

            while (true) {
                try {
                    synchronized (mutex) {
                        StringBuilder msg = new StringBuilder();
                        msg.append(CHARS.charAt(index));
                        msg.append(id);
                        System.out.println(msg);

                        index = (index + 1) % CHARS.length();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        ArrayList<Runner> runners = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            Runner runner = new Runner(i);
            Thread thread = new Thread(runner);
            thread.start();
            runners.add(runner);
        }
    }
}
