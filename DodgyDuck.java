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
        backgroundImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\skyBackground.png").getImage();
        duckImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\Duck1.PNG").getImage();
        SkyTubeImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\SkyPipe.png").getImage();
        FloorTubeImg = new ImageIcon("C:\\Users\\suhai\\OneDrive - cord.edu\\CSC\\finaProjectMenu\\src\\FloorPipe.png").getImage();
    }

    private void setupGame() {
        setFocusable(true);
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
    }

    private void updateGame() {
        backgroundX -= pipeSpeed;
        if (backgroundX <= -getWidth()) backgroundX = 0;

        if (pipes.isEmpty()) {
            for (int i = 0; i < 3; i++) {
                pipes.add(new Pipe(getWidth() + i * 300));
            }
        }

        ArrayList<Pipe> newPipes = new ArrayList<>();
        for (Pipe pipe : pipes) {
            pipe.move();
            if (pipe.x + pipe.width > 0) newPipes.add(pipe);
        }

        if (pipes.get(0).x + pipes.get(0).width < 0) {
            newPipes.add(new Pipe(getWidth()));
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
            newGameFrame.add(new DodgyDuck(pipeSpeed, gapSize));
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(backgroundImg, backgroundX, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImg, backgroundX + getWidth(), 0, getWidth(), getHeight(), this);
        g.drawImage(duckImg, duck.x, duck.y, duck.width, duck.height, this);

        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
    }
}
