import java.awt.*;
import javax.swing.*;
public class Egg {
    // Properties
    private int x, y;         // Position
    private int size = 10;    // Default size
    private int speed = 2;    // Default speed
    private int score = 5;    // Points for collecting
    private Color color = Color.WHITE;  // Color

    //Image
    Image EggImg;
    // Constructor (default)
    public Egg() {}

    // Constructor (custom values)
    public Egg(int x, int y, int size, int speed, int score, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.score = score;
        this.color = color;
        loadImage();
    }

    //Draws the egg
    private void loadImage() {
        EggImg = new ImageIcon("assets/Egg.PNG").getImage();
    }

    // Moves left
    public void move() {
        x -= speed;
    }

    //  Draw the egg
    public void draw(Graphics g) {

        g.drawImage(EggImg, x, y, null);
    }

    // Check if duck collected the egg
    public boolean checkCollision(Rectangle duckRect) {
        Rectangle eggRect = new Rectangle(x, y, size, size);
        return eggRect.intersects(duckRect);
    }

    // Getter and setter methods
    public int getX()
    { return x;
    }
    public int getY()
    { return y;
    }
    public int getSize()
    { return size;
    }
    public int getScore()
    { return score;
    }
}
