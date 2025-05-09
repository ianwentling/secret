import javax.swing.*;
import java.awt.*;
public class MediumMode extends JFrame implements DifficultyMode{

    public int getPipeSpeed()
    {return 5;}
    public int getGapSize()
    {return 10; }
    public double getGravity()
    {return 0.5;}




    public MediumMode()
    {
        setTitle("Medium Mode");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center Window

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Ahmed MediumMode", SwingConstants.CENTER );
        panel.add(label, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new GameMenu(); // Reopen the main menu
        });

        panel.add(backButton, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);

        DodgyDuck dodgyDuck = new DodgyDuck();
        panel.add(dodgyDuck, BorderLayout.CENTER);
    }
}
