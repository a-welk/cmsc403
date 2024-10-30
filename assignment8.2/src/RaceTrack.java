import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.util.concurrent.locks.ReentrantLock;

import java.util.Random;

public class RaceTrack extends JPanel {

    final private static int finishLine = 350;
    private static ReentrantLock lock = new ReentrantLock();

    private static JFrame frame;
    private static JButton startButton;
    private static JButton pauseButton;
    private static JButton resetButton;
    private static BufferedImage car;

    private static boolean move;

    private static int racer1;
    private static int racer2;
    private static int racer3;

    public static void main(String argv[]) {

        /**
         * Initalize window settings
         */
        frame = new JFrame("Race Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        /**
         * Create the track to place objects
         */
        RaceTrack track = new RaceTrack();
        frame.setContentPane(track);

        /**
         * Add buttons to the screen for starting and stopping the race
         */
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            startRacers();
        });
        panel.add(startButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            pauseRacers();
        });
        panel.add(pauseButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            pauseRacers();
            resetDistances();
        });

        panel.add(resetButton);

        try {
            car = ImageIO.read(new File("sportive-car.png"));
        } catch (IOException ex) {
            System.out.println("IO error");
            System.exit(1);
        }

        frame.add(panel);

        /**
         * Create racers for the track
         */

        racer1 = 0;
        racer2 = 0;
        racer3 = 0;

        move = false;
        /**
         * Start racer threads to wait for the start
         */

        new Thread(() -> {
            Random rand = new Random();
            while (true) {
                if (move) {
                    if (racer1 < finishLine) {
                        int deltaX = rand.nextInt(11);

                        if (racer1 + deltaX > finishLine) {
                            racer1 = finishLine;
                        } else {
                            racer1 += deltaX;
                        }
                    }

                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            Random rand = new Random();
            while (true) {
                if (move) {
                    if (racer2 < finishLine) {
                        int deltaX = rand.nextInt(11);

                        if (racer2 + deltaX > finishLine) {
                            racer1 = finishLine;
                        } else {
                            racer2 += deltaX;
                        }

                    }

                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            Random rand = new Random();
            while (true) {
                if (move) {
                    if (racer3 < finishLine) {
                        int deltaX = rand.nextInt(11);

                        if (racer3 + deltaX > finishLine) {
                            racer1 = finishLine;
                        } else {
                            racer3 += deltaX;
                        }

                    }

                }

                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    return;
                }
            }
        }).start();

        /**
         * Size and show the window
         */
        frame.setSize(500, 200);
        frame.repaint();
        frame.setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the 3 rectangle for the "tracks"
        g.setColor(Color.GRAY);
        g.fillRect(25, 55, 400, 15);
        g.fillRect(25, 95, 400, 15);
        g.fillRect(25, 135, 400, 15);

        lock.lock();
        try {
            // Draw the images at the beginning of the track
            g.drawImage(car, racer1 + 25, 50, this);
            g.drawImage(car, racer2 + 25, 90, this);
            g.drawImage(car, racer3 + 25, 130, this);
        } finally {
            lock.unlock();
        }


        int winner = isWinner();
        if (winner > 0) {
            resetDistances();
            JOptionPane.showMessageDialog(frame, "We have a winner: car " + winner);

        }

        repaint();

    }

    public static void resetDistances() {
        lock.lock();

        try{
            move = false;

            racer1 = 0;
            racer2 = 0;
            racer3 = 0;

        } finally {
            lock.unlock();
        }
    }

    public static void startRacers() {
        lock.lock();

        try {
            move = true;
        } finally {
            lock.unlock();
        }
    }

    public static void pauseRacers() {
        lock.lock();

        try {
            move = false;
        } finally {
            lock.unlock();
        }
    }

    public static int isWinner() {
        lock.lock();

        try {
            if (racer1 >= finishLine) {
                return 1;
            }
            if (racer2 >= finishLine) {
                return 2;
            }
            if (racer3 >= finishLine) {
                return 3;
            }

            return 0;

        } finally {
            lock.unlock();
        }
    }

}