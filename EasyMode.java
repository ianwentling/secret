import javax.swing.*;
import java.awt.*;
public class EasyMode extends JFrame implements DifficultyMode {

    public int getPipeSpeed()

    {return 3;}
    public int getGapSize()
    {return 50;}
    public double getGravity()
    {return 0.4;}

 private JFrame frame;

    public EasyMode()
    {
        setTitle("Easy Mode");
        setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }




}
