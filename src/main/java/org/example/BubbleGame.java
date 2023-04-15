package org.example;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BubbleGame extends JPanel {
    private static final long serialVersionUID = 1L;
    private ArrayList<Bubble> bubbles;
    private int score;

    public BubbleGame() {
        bubbles = new ArrayList<Bubble>();
        addMouseListener(new BubbleClickListener());
        new Thread(new BubbleMaker()).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (Bubble b : bubbles) {
            g.setColor(b.getColor());
            g.fillOval(b.getX(), b.getY(), b.getSize(), b.getSize());
        }
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 20);
    }

    private class Bubble {
        private int x, y, size;
        private Color color;

        public Bubble(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getSize() { return size; }
        public Color getColor() { return color; }
    }

    private class BubbleMaker implements Runnable {
        public void run() {
            Random rand = new Random();
            while (true) {
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                int x = rand.nextInt(getWidth() - 50);
                int y = rand.nextInt(getHeight() - 50);
                int size = rand.nextInt(50) + 20;
                Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                bubbles.add(new Bubble(x, y, size, color));
                repaint();
            }
        }
    }

    private class BubbleClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            for (int i = 0; i < bubbles.size(); i++) {
                Bubble b = bubbles.get(i);
                if (e.getX() >= b.getX() && e.getX() <= b.getX() + b.getSize() &&
                        e.getY() >= b.getY() && e.getY() <= b.getY() + b.getSize()) {
                    bubbles.remove(i);
                    score++;
                    break;
                }
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new BubbleGame());
        frame.setVisible(true);
    }
}

