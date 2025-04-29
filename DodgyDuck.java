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
    int eggSpawnRate = 100;

    // Objects
    Duck duck;
    ArrayList<Pipe> pipes = new ArrayList<>();
    ArrayList<Egg> eggs = new ArrayList<>();
    Timer timer;
    Timer scoreTimer;
    Random rand = new Random();

    // Score
    private JLabel scoreLabel;
    private int scoreSeconds = 0;

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
            int minHeight = 50;
            int maxTop = getHeight() - gapSize - minHeight;
            heightTop = rand.nextInt(Math.max(maxTop, 1)) + minHeight;
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

    public DodgyDuck() {
        this(2, 120, 100);
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
        backgroundImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\skyBackground.png").getImage();
        duckImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\Duck1.PNG").getImage();
        SkyTubeImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\SkyPipe.png").getImage();
        FloorTubeImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\FloorPipe.png").getImage();
    }

    private void setupGame() {
        setLayout(new BorderLayout());
        setFocusable(true);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Minecraft", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel, BorderLayout.NORTH);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) duck.y -= 20;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) duck.y += 20;
            }
        });

        timer = new Timer(20, e -> {
            updateGame();
            repaint();
        });
        timer.start();

        scoreTimer = new Timer(1000, e -> {
            scoreSeconds++;
            scoreLabel.setText("Score: " + scoreSeconds);
        });
        scoreTimer.start();
    }

    private void updateGame() {
        backgroundX -= pipeSpeed;
        if (backgroundX <= -getWidth()) backgroundX = 0;

        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).x < getWidth() - 300) {
            pipes.add(new Pipe(getWidth()));
        }

        ArrayList<Pipe> newPipes = new ArrayList<>();
        for (Pipe pipe : pipes) {
            pipe.move();
            if (pipe.x + pipe.width > 0) newPipes.add(pipe);
        }
        pipes = newPipes;

        for (Pipe pipe : pipes) {
            if (duck.x + duck.width > pipe.x && duck.x < pipe.x + pipe.width) {
                if (duck.y < pipe.heightTop || duck.y + duck.height > pipe.heightTop + gapSize) {
                    gameOver();
                }
            }
        }

        if (duck.y < 0 || duck.y + duck.height > getHeight()) {
            gameOver();
        }

        ArrayList<Egg> newEggs = new ArrayList<>();
        for (Egg egg : eggs) {
            egg.move();
            if (egg.getX() + egg.getSize() > 0) newEggs.add(egg);
        }
        eggs = newEggs;

        if (rand.nextInt(eggSpawnRate) == 0) {
            int eggSize = 15;
            int attempts = 0;
            boolean valid = false;
            int eggY = 0;

            while (!valid && attempts < 10) {
                eggY = rand.nextInt(getHeight() - eggSize);
                Rectangle eggRect = new Rectangle(getWidth(), eggY, eggSize, eggSize);
                valid = true;

                for (Pipe pipe : pipes) {
                    Rectangle topPipeRect = new Rectangle(pipe.x, 0, pipe.width, pipe.heightTop);
                    Rectangle bottomPipeRect = new Rectangle(pipe.x, pipe.heightTop + gapSize, pipe.width, pipe.heightBottom);

                    if (eggRect.intersects(topPipeRect) || eggRect.intersects(bottomPipeRect)) {
                        valid = false;
                        break;
                    }
                }
                attempts++;
            }

            if (valid) {
                eggs.add(new Egg(getWidth(), eggY, eggSize, pipeSpeed, 5, Color.YELLOW));
            }
        }


        Rectangle duckBounds = duck.getBounds();
        ArrayList<Egg> collectedEggs = new ArrayList<>();
        for (Egg egg : eggs) {
            if (egg.checkCollision(duckBounds)) {
                scoreSeconds += egg.getScore();
                collectedEggs.add(egg);
            }
        }
        eggs.removeAll(collectedEggs);

        scoreLabel.setText("Score: " + scoreSeconds);
    }

    private void gameOver() {
        timer.stop();
        scoreTimer.stop();

        JDialog gameOverDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game Over!! " + scoreSeconds, true);
        gameOverDialog.setSize(300, 250);
        gameOverDialog.setLayout(new GridLayout(3, 1, 10, 10));
        gameOverDialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBackground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("Score Is " + scoreSeconds, SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Minecraft", Font.BOLD, 22));
        gameOverLabel.setForeground(Color.WHITE);
        panel.add(gameOverLabel);

        JButton restartButton = new JButton("Restart");
        JButton menuButton = new JButton("Back to Menu");

        restartButton.setFont(new Font("Minecraft", Font.BOLD, 20));
        menuButton.setFont(new Font("Minecraft", Font.BOLD, 20));

        restartButton.setBackground(Color.BLACK);
        restartButton.setForeground(Color.RED);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setContentAreaFilled(false);

        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(Color.GREEN);
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);

        restartButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { restartButton.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { restartButton.setForeground(Color.RED); }
        });

        menuButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { menuButton.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { menuButton.setForeground(Color.GREEN); }
        });

        panel.add(restartButton);
        panel.add(menuButton);

        gameOverDialog.setContentPane(panel);

        restartButton.addActionListener(e -> {
            gameOverDialog.dispose();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();

            JFrame newGameFrame = new JFrame("Dodgy Duck");
            newGameFrame.setSize(700, 700);
            newGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newGameFrame.setLocationRelativeTo(null);
            newGameFrame.add(new DodgyDuck(pipeSpeed, gapSize, eggSpawnRate));
            newGameFrame.setVisible(true);
        });

        menuButton.addActionListener(e -> {
            gameOverDialog.dispose();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new GameMenu();
        });

        gameOverDialog.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(backgroundImg, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImg, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);
        g.drawImage(duckImg, duck.x, duck.y, duck.width, duck.height, this);

        for (Pipe pipe : pipes) pipe.draw(g);
        for (Egg egg : eggs) egg.draw(g);
    }
}
