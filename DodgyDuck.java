import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class DodgyDuck extends JPanel {

    // Images
    Image backgroundImg, duckImg, SkyTubeImg, FloorTubeImg;

    // Duck settings
    int duckX = 20;
    int duckY = 150;
    int duckWidth = 50;
    int duckHeight = 40;

    // Scroll
    private int backgroundX = 0;

    // Game config
    int pipeSpeed = 2;
    int gapSize = 120;

    // Objects
    Duck duck;
    ArrayList<Pipe> pipes = new ArrayList<>();
    Timer timer;

    // Duck object
    class Duck {
        int x = duckX;
        int y = duckY;
        int width = duckWidth;
        int height = duckHeight;
        Image img;

        Duck(Image img) {
            this.img = img;
        }
    }

    // Pipe object
    class Pipe {
        int x, heightTop, heightBottom;
        int width = 100;

        Pipe(int x) {
            this.x = x;

            // Random pipe height
            int minHeight = 50;
            int maxTop = getHeight() - gapSize - minHeight;
            heightTop = new Random().nextInt(Math.max(maxTop, 1)) + minHeight;
            heightBottom = getHeight() - heightTop - gapSize;
        }

        void move() {
            this.x -= pipeSpeed;
        }

        void draw(Graphics g) {
            g.drawImage(SkyTubeImg, x, 0, width, heightTop, null);
            g.drawImage(FloorTubeImg, x, heightTop + gapSize, width, heightBottom, null);
        }
    }

    // Constructors
    public DodgyDuck() {
        this(2, 120);  // Default difficulty
    }

    public DodgyDuck(int pipeSpeed, int gapSize) {
        this.pipeSpeed = pipeSpeed;
        this.gapSize = gapSize;

        loadAssets();
        duck = new Duck(duckImg);
        setupGame();
    }

    private void loadAssets() {
        backgroundImg = new ImageIcon("assets/Background.png").getImage();
        duckImg = new ImageIcon("assets/Duck1.png").getImage();
        SkyTubeImg = new ImageIcon("assets/SkyPipe.png").getImage();
        FloorTubeImg = new ImageIcon("assets/FloorPipe.png").getImage();
    }

    private void setupGame() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) duck.y -= 20;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) duck.y += 20;
            }
        });

        // Start game loop
        timer = new Timer(20, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    private void updateGame() {
        // Scroll background
        backgroundX -= pipeSpeed;
        if (backgroundX <= -getWidth()) backgroundX = 0;

        // Add pipes if empty
        if (pipes.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                pipes.add(new Pipe(getWidth() + i * 300));
            }
        }

        // Move and recycle pipes
        ArrayList<Pipe> newPipes = new ArrayList<>();
        for (Pipe pipe : pipes) {
            pipe.move();
            if (pipe.x + pipe.width > 0) newPipes.add(pipe);
        }

        if (pipes.get(0).x + pipes.get(0).width < 0) {
            newPipes.add(new Pipe(getWidth()));
        }

        pipes = newPipes;

        // Check for collisions
        for (Pipe pipe : pipes) {
            if (duck.x + duck.width > pipe.x && duck.x < pipe.x + pipe.width) {
                if (duck.y < pipe.heightTop || duck.y + duck.height > pipe.heightTop + gapSize) {
                    gameOver();
                }
            }
        }

        // Check boundaries
        if (duck.y < 0 || duck.y + duck.height > getHeight()) {
            gameOver();
        }
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0); // or return to menu
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // Draw scrolling background
        g.drawImage(backgroundImg, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImg, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);

        // Draw duck
        g.drawImage(duckImg, duck.x, duck.y, duck.width, duck.height, this);

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
    }
}