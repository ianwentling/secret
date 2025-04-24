import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    //Score label
    JLabel scoreLabel;
    int scoreSeconds = 0;

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

    // Images
    backgroundImg = new ImageIcon("assets/Background.png").getImage();
    duckImg = new ImageIcon("assets/Duck1.png").getImage();
    SkyTubeImg = new ImageIcon("assets/SkyTube.png").getImage();
    FloorTubeImg = new ImageIcon("assets/FloorTube.png").getImage();
   //Set up score label
    scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
    scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
    scoreLabel.setForeground(Color.BLACK);
    add(scoreLabel, BorderLayout.NORTH);

    //Start Timer here
    Timer timer = new Timer(1000,new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            scoreSeconds++;
            scoreLabel.setText("Score: " + scoreSeconds);
            repaint();
        }
    });
    timer.start();


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

