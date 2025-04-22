import java.awt.*;
import javax.swing.*;
public class DodgyDuck extends JPanel {

    //Images
    Image backgroundImg;
    Image duckImg;
    Image SkyTubeImg;
    Image FloorTubeImg;

    //Duck

    int duckX = 20;
    int duckY = 150;
    int duckWidth = 50;
    int duckHeight = 40;

    class Duck
    {
        int x = duckX;
        int y = duckY;
        int width = duckWidth;
        int height = duckHeight;
        Image img;

        Duck(Image img)
        {
            this.img = img;
        }
    }

    //Game Logic
    Duck duck;

    DodgyDuck() {


        backgroundImg = new ImageIcon("assets/Background.png").getImage();
        duckImg = new ImageIcon("assets/Duck1.png").getImage();
        SkyTubeImg = new ImageIcon("assets/SkyTube.png").getImage();
        FloorTubeImg = new ImageIcon("assets/FloorTube.png").getImage();

        //duck
        duck = new Duck(duckImg);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);

        //Duck
        g.drawImage(duckImg, duckX, duckY, duckWidth, duckHeight, this);

        //SkyTube
        g.drawImage(SkyTubeImg,400,10,100,300, this);

        //FloorTube
        g.drawImage(FloorTubeImg,400,150,100,300, this);
    }

}
