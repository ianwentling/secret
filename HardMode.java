import javax.swing.*;
import java.awt.*;
public class HardMode extends JFrame implements DifficultyMode{


    public int getPipeSpeed()
    {return 6;}
    public int getGapSize()
    {return 5;}
    public double getGravity()
    {return 0.7;}





    public HardMode()
    {
        setTitle("Easy Mode");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center Window

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Ahmed HardMode", SwingConstants.CENTER );
        panel.add(label, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new GameMenu(); // Reopen the main menu
        });

        panel.add(backButton, BorderLayout.SOUTH);
        add(panel);
        setVisible(true);
    }
}
