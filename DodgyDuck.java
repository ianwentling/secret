import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class DodgyDuck extends JPanel {
    // Images
    Image backgroundImg;
    Image duckImg;
    Image SkyTubeImg;
    Image FloorTubeImg;

    // Duck
    int duckX = 20;
    int duckY = 150;
    int duckWidth = 50;
    int duckHeight = 40;
    private String difficulty;  // Difficulty level

    // Game Logic
    Duck duck;

    // Pipe variables
    int pipeSpeed;
    int gapHeight = 150;  // Space between the pipes
    ArrayList<Pipe> pipes;

    // Timer and game loop
    Timer timer;

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

    class Pipe {
        int x;
        int yTop;
        int yBottom;
        int width = 100;
        int heightTop;
        int heightBottom;
        int gapHeight = 100;  // Set the gap height (adjust as needed)

        Pipe(int x) {
            this.x = x;

            // Ensure the random height is always within a valid range
            int maxHeight = getHeight() - gapHeight - 1;
            if (maxHeight <= 0) {
                throw new IllegalArgumentException("Invalid screen height or gapHeight: maxHeight must be positive.");
            }

            this.heightTop = new Random().nextInt(maxHeight); // Random height for the top pipe
            this.heightBottom = getHeight() - this.heightTop - gapHeight;

            this.yTop = 0;
            this.yBottom = getHeight() - this.heightBottom;
        }

        // Move the pipe to the left
        public void move() {
            this.x -= pipeSpeed; // Move pipes based on the current speed
        }

        public void draw(Graphics g) {
            g.drawImage(SkyTubeImg, x, yTop, width, heightTop, null); // Adjust the image based on the class context
            g.drawImage(FloorTubeImg, x, yBottom, width, heightBottom, null);
        }
    }

    // Constructor with difficulty
    public DodgyDuck(String difficulty) {
        this.difficulty = difficulty;

        // Set difficulty-related values (like speed)
        setDifficulty(difficulty);

        // Load images
        backgroundImg = new ImageIcon("Assets/Background.PNG").getImage();
        duckImg = new ImageIcon("Assets/Duck1.PNG").getImage();
        SkyTubeImg = new ImageIcon("Assets/SkyTube.PNG").getImage();
        FloorTubeImg = new ImageIcon("Assets/FloorTube.PNG").getImage();

        // Initialize the duck object
        duck = new Duck(duckImg);

        // Initialize pipes only after the panel size is determined
        pipes = new ArrayList<>();

        // Add key listener for up and down arrow keys
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    duck.y -= 10;  // Move up
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    duck.y += 10;  // Move down
                }
            }
        });

        // Timer to update game state every 20 milliseconds (50 FPS)
        timer = new Timer(20, e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    // Adjust the difficulty speed
    private void setDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                pipeSpeed = 2;
                break;
            case "medium":
                pipeSpeed = 4;
                break;
            case "hard":
                pipeSpeed = 6;
                break;
            default:
                pipeSpeed = 3;  // Default to medium speed
        }
    }

    // Update the game state (move pipes, check for collisions)
    private void updateGame() {
        // Move the pipes
        for (Pipe pipe : pipes) {
            pipe.move();
        }

        // Check for collisions (simple collision detection)
        for (Pipe pipe : pipes) {
            if (duck.x + duck.width > pipe.x && duck.x < pipe.x + pipe.width) {
                if (duck.y < pipe.heightTop || duck.y + duck.height > pipe.yBottom) {
                    gameOver();  // Game Over if collision happens
                }
            }
        }

        // Check if duck goes out of bounds
        if (duck.y < 0 || duck.y + duck.height > getHeight()) {
            gameOver();  // Game Over if duck goes out of bounds
        }

        // Add new pipes when the existing ones move off-screen
        if (pipes.get(0).x + pipes.get(0).width < 0) {
            pipes.remove(0);
            pipes.add(new Pipe(getWidth()));
        }
    }

    // Game Over logic
    private void gameOver() {
        timer.stop();  // Stop the game loop (timer)
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);  // Exit the game (can be replaced with a reset logic)
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);

        // Duck
        g.drawImage(duckImg, duck.x, duck.y, duck.width, duck.height, this);

        // Draw pipes after the panel is sized
        if (getHeight() > 0) {
            // Initialize pipes if the panel has valid height
            if (pipes.isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    pipes.add(new Pipe(getWidth() + (i * 300)));
                }
            }
        }

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);  // Draw each pipe
        }
    }
}
