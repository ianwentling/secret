import javax.swing.*;
import java.awt.*;
public class HardMode extends JFrame implements DifficultyMode{


    public int getPipeSpeed()
    {return 6;}
    public int getGapSize()
    {return 5;}
    public double getGravity()
    {return 0.7;}



    private JFrame frame;

    public HardMode()
    {
        setTitle("Hard Mode");
        setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
