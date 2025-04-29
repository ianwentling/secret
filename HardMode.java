import javax.swing.*;

public class HardMode extends JFrame implements DifficultyMode {

    public int getPipeSpeed() { return 5; }
    public int getGapSize() { return 90; }
    public double getGravity() { return 0.8; }
    public int getEggSpawnRate() { return 140; } // fewer eggs

    public HardMode() {
        setTitle("Hard Mode");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DodgyDuck gamePanel = new DodgyDuck(getPipeSpeed(), getGapSize(), getEggSpawnRate());
        add(gamePanel);

        setVisible(true);
    }
}

