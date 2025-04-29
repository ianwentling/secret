import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class DodgyDuck extends JPanel {

    private AudioPlayerInterface player;  // audio player for the WHOOOLE class

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
        int x = duckX, y = duckY, width = duckWidth, height = duckHeight;
        Image img;
        Duck(Image img) { this.img = img; }
        Rectangle getBounds() {
            return new Rectangle(x, y, width, height);
        }
    }

    // Pipe object
    class Pipe {
        int x, heightTop, heightBottom, width = 100;
        Pipe(int x) {
            this.x = x;
            int minHeight = 50;
            int maxTop = getHeight() - gapSize - minHeight;
            heightTop = rand.nextInt(Math.max(maxTop, 1)) + minHeight;
            heightBottom = getHeight() - heightTop - gapSize;
        }
        void move() { x -= pipeSpeed; }
        void draw(Graphics g) {
            g.drawImage(SkyTubeImg, x, 0, width, heightTop, null);
            g.drawImage(FloorTubeImg, x, heightTop + gapSize, width, heightBottom, null);
        }
    }

    // Constructors
    public DodgyDuck() {
        this(2, 120, 100);
    }

    public DodgyDuck(int pipeSpeed, int gapSize, int eggSpawnRate) {
        this.pipeSpeed = pipeSpeed;
        this.gapSize = gapSize;
        this.eggSpawnRate = eggSpawnRate;

        loadAssets();
        duck = new Duck(duckImg);

        // get that audio in there
        player = new AudioPlayer();
        player.play("Assets/song.wav");

        setupGame();
    }

    private void loadAssets() {
        backgroundImg = new ImageIcon("Assets/Background.PNG").getImage();
        duckImg       = new ImageIcon("Assets/Duck1.PNG").getImage();
        SkyTubeImg    = new ImageIcon("Assets/SkyPipe.png").getImage();
        FloorTubeImg  = new ImageIcon("Assets/FloorPipe.png").getImage();
    }

    private void setupGame() {
        setLayout(new BorderLayout());
        setFocusable(true);

        // Score label
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel, BorderLayout.NORTH);

        // Key controls
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP)   duck.y -= 20;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) duck.y += 20;
            }
        });

        // Game loop timer
        timer = new Timer(20, e -> {
            updateGame();
            repaint();
        });
        timer.start();

        // Score timer
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

        // Spawn/move pipes
        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < getWidth() - 300) {
            pipes.add(new Pipe(getWidth()));
        }
        ArrayList<Pipe> newPipes = new ArrayList<>();
        for (Pipe p : pipes) {
            p.move();
            if (p.x + p.width > 0) newPipes.add(p);
        }
        pipes = newPipes;

        // Collision with pipes
        for (Pipe p : pipes) {
            if (duck.x + duck.width > p.x && duck.x < p.x + p.width) {
                if (duck.y < p.heightTop || duck.y + duck.height > p.heightTop + gapSize) {
                    gameOver();
                }
            }
        }

        // Boundary check
        if (duck.y < 0 || duck.y + duck.height > getHeight()) {
            gameOver();
        }

        // Eggs movement & spawn
        ArrayList<Egg> newEggs = new ArrayList<>();
        for (Egg egg : eggs) {
            egg.move();
            if (egg.getX() + egg.getSize() > 0) newEggs.add(egg);
        }
        eggs = newEggs;
        if (rand.nextInt(eggSpawnRate) == 0) {
            int y = rand.nextInt(getHeight() - 50);
            eggs.add(new Egg(getWidth(), y, 15, pipeSpeed, 5, Color.WHITE));
        }

        // Egg collection
        Rectangle db = duck.getBounds();
        ArrayList<Egg> collected = new ArrayList<>();
        for (Egg egg : eggs) {
            if (egg.checkCollision(db)) {
                scoreSeconds += egg.getScore();
                collected.add(egg);
            }
        }
        eggs.removeAll(collected);

        scoreLabel.setText("Score: " + scoreSeconds);
    }

    private void gameOver() {
        timer.stop();

        JDialog gameOverDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game Over", true);
        gameOverDialog.setSize(300, 250);
        gameOverDialog.setLayout(new GridLayout(3, 1, 10, 10));
        gameOverDialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBackground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Minecraft", Font.BOLD, 22));
        gameOverLabel.setForeground(Color.WHITE);
        panel.add(gameOverLabel);

        JButton restartButton = new JButton("Restart");
        JButton menuButton = new JButton("Back to Menu");

        restartButton.setFont(new Font("Minecraft", Font.BOLD, 20));
        menuButton.setFont(new Font("Minecraft", Font.BOLD, 20));

        // Restart button style
        restartButton.setBackground(Color.BLACK);
        restartButton.setForeground(Color.RED);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setContentAreaFilled(false);

        // Menu button style
        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(Color.GREEN);
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);

        // Hover effects
        restartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                restartButton.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                restartButton.setForeground(Color.RED);
            }
        });

        menuButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuButton.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                menuButton.setForeground(Color.GREEN);
            }
        });

        panel.add(restartButton);
        panel.add(menuButton);

        gameOverDialog.setContentPane(panel);

        // Restart the game
        restartButton.addActionListener(e -> {
            gameOverDialog.dispose();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();

            JFrame newGameFrame = new JFrame("Dodgy Duck");
            newGameFrame.setSize(700, 700);
            newGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newGameFrame.setLocationRelativeTo(null);
            newGameFrame.add(new DodgyDuck(pipeSpeed, gapSize,100));
            newGameFrame.setVisible(true);
        });

        // Back to menu
        menuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new GameMenu();
        });

        gameOverDialog.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background twice for scrolling effect
        g.drawImage(backgroundImg, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImg, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);

        // Draw duck
        g.drawImage(duckImg, duck.x, duck.y, duck.width, duck.height, this);

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        // Draw eggs
        for (Egg egg : eggs) {
            egg.draw(g);
        }
    }
}
