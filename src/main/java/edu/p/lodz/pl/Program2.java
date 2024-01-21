package edu.p.lodz.pl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Program2 {
    private static class Runner implements Runnable {
        private final static String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        private final int id;
        private volatile boolean suspended = true;

        public Runner(int id) {
            this.id = id;
        }

        synchronized void suspend() {
            suspended = true;
        }

        synchronized void resume() {
            suspended = false;
            notify();
        }

        @Override
        public void run() {
            int index = 0;

            while (true) {
                try {
                    synchronized (this) {
                        while (suspended) {
                            wait();
                        }
                    }

                    StringBuilder msg = new StringBuilder();
                    msg.append(CHARS.charAt(index));
                    msg.append(id);
                    System.out.println(msg);

                    index = (index + 1) % CHARS.length();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {

        ArrayList<Runner> runners = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            Runner runner = new Runner(i);
            Thread thread = new Thread(runner);
            thread.start();
            runners.add(runner);
        }

        JFrame frame = new JFrame("");

        JTextField textField = new JTextField(20);
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(e -> {
            String text = textField.getText();
            int id = Integer.parseInt(text);
            runners.get(id).resume();
        });

        stopButton.addActionListener(e -> {
            String text = textField.getText();
            int id = Integer.parseInt(text);
            runners.get(id).suspend();
        });


        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(textField);
        panel.add(startButton);
        panel.add(stopButton);

        frame.setSize(200,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
