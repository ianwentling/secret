import javax.swing.*;

public class EasyMode extends JFrame implements DifficultyMode {

    public int getPipeSpeed() { return 3; }
    public int getGapSize() { return 150; }
    public double getGravity() { return 0.4; }
    public int getEggSpawnRate() { return 60; }  // more eggs

    public EasyMode() {
        setTitle("Easy Mode");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DodgyDuck gamePanel = new DodgyDuck(getPipeSpeed(), getGapSize(), getEggSpawnRate());
        add(gamePanel);

        setVisible(true);
    }
}
