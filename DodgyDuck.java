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

    // Instance variables for score
    private JLabel scoreLabel;
    private int scoreSeconds = 0;
    private Timer scoreTimer;
    private int eggSpawnRate = 100;  // default easy mode

    // Scroll
    private int backgroundX = 0;

    // Game config
    int pipeSpeed = 2;
    int gapSize = 120;

    // Objects
    Duck duck;
    ArrayList<Pipe> pipes = new ArrayList<>();
    ArrayList<Egg> eggs = new ArrayList<>();
    Timer timer;
    Random rand = new Random();

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

        Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
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
        this(2, 120,100);  // Default difficulty
    }

    public DodgyDuck(int pipeSpeed, int gapSize, int eggSpawnRate) {
        this.pipeSpeed = pipeSpeed;
        this.gapSize = gapSize;
        this.eggSpawnRate = eggSpawnRate;

        loadAssets();
        duck = new Duck(duckImg);
        setupGame();
    }

    private void loadAssets() {
        backgroundImg = new ImageIcon("Assets/Background.PNG").getImage();
        duckImg = new ImageIcon("Assets/Duck1.PNG").getImage();
        SkyTubeImg = new ImageIcon("Assets/SkyPipe.png").getImage();
        FloorTubeImg = new ImageIcon("Assets/FloorPipe.png").getImage();


    }

    private void setupGame() {
        setFocusable(true);
        //Score label setup
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER );
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel, BorderLayout.NORTH);

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
        AudioPlayerInterface player = new AudioPlayer();
        player.play("Assets/song.wav");

     //Score Timer
        scoreTimer = new Timer(1000, e -> {
         scoreSeconds++;
         scoreLabel.setText("Score: " + scoreSeconds);

        });
        scoreTimer.start();

    }

    private void updateGame() {
        // Scroll background
        backgroundX -= pipeSpeed;
        if (backgroundX <= -getWidth()) backgroundX = 0;

        // Only add a new pipe if the last one has moved enough
        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < getWidth() - 300) {
            pipes.add(new Pipe(getWidth()));
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

        //Move Eggs
        ArrayList<Egg> newEggs = new ArrayList<>();
        for (Egg egg : eggs) {
            egg.move();
            if (egg.getX() + egg.getSize() > 0) newEggs.add(egg);
        }
        eggs = newEggs;

        // Spawn egg occasionally
        if (rand.nextInt(eggSpawnRate) == 0) {  // Chances of egg spawning
            int randomY = rand.nextInt(getHeight() - 50);
            eggs.add(new Egg(getWidth(), randomY, 15, pipeSpeed, 5, Color.WHITE));
        }

        // Check for egg collection
        Rectangle duckBounds = duck.getBounds();
        ArrayList<Egg> collectedEggs = new ArrayList<>();
        for (Egg egg : eggs) {
            if (egg.checkCollision(duckBounds)) {
                scoreSeconds+= egg.getScore();
                collectedEggs.add(egg);
            }
        }
        eggs.removeAll(collectedEggs);

        //Update scoreLabel
        scoreLabel.setText("Score: " + scoreSeconds);
    }

    private void gameOver() {
        timer.stop();
        scoreTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + scoreSeconds);
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
        //Draw eggs
        for (Egg egg : eggs) {
            egg.draw(g);
        }
       

    }
}