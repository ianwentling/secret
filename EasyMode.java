import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EasyMode extends JFrame implements DifficultyMode {

    public int getPipeSpeed()

    {return 3;}
    public int getGapSize()
    {return 50;}
    public double getGravity()
    {return 0.4;}


    public EasyMode()
    {

        setTitle("Easy Mode");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center Window

        JPanel panel = new JPanel(new BorderLayout());


        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new GameMenu(); // Reopen the main menu
        });

        panel.add(backButton, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);
        // panel.setBackground(Color.CYAN);

       DodgyDuck dodgyDuck = new DodgyDuck();
        panel.add(dodgyDuck, BorderLayout.CENTER);
//        pack();
//        panel.setVisible(true);

    }




}
