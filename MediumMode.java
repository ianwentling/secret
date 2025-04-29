import javax.swing.*;

public class MediumMode extends JFrame implements DifficultyMode {

    public int getPipeSpeed() { return 4; }
    public int getGapSize() { return 120; }
    public double getGravity() { return 0.6; }
    public int getEggSpawnRate() { return 100; }

    public MediumMode() {
        setTitle("Medium Mode");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DodgyDuck gamePanel = new DodgyDuck(getPipeSpeed(), getGapSize(), getEggSpawnRate());
        add(gamePanel);

        setVisible(true);
    }
}
