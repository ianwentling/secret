import javax.swing.*;
import java.awt.*;
public class MediumMode extends JFrame implements DifficultyMode{

    public int getPipeSpeed()
    {return 5;}
    public int getGapSize()
    {return 10; }
    public double getGravity()
    {return 0.5;}


    private JFrame frame;

    public MediumMode()
    {
        setTitle("Medium Mode");
        setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
