import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu {
    private JFrame frame;

    public GameMenu() {
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(5, 1)); // Increased grid rows for the new label
        frame.setLocationRelativeTo(null); // Centers the window on the screen

        // Create a label to display the selected difficulty
        JLabel difficultyLabel = new JLabel("Select Game Difficulty", SwingConstants.CENTER);
        frame.add(difficultyLabel);

        // Create difficulty buttons
        JButton easyButton = new JButton("Easy");
        easyButton.setBackground(Color.GREEN);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame("easy");
            }
        });

        JButton mediumButton = new JButton("Medium");
        mediumButton.setBackground(Color.YELLOW);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame("medium");
            }
        });

        JButton hardButton = new JButton("Hard");
        hardButton.setBackground(Color.RED);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame("hard");
            }
        });

        // Add the buttons to the frame
        frame.add(easyButton);
        frame.add(mediumButton);
        frame.add(hardButton);

        frame.setVisible(true);
    }

    private void openGame(String difficulty) {
        frame.setVisible(false);  // Hide the menu
        JFrame gameFrame = new JFrame("Dodgy Duck - " + difficulty);
        gameFrame.add(new DodgyDuck(difficulty));  // Pass the difficulty to DodgyDuck
        gameFrame.setSize(600, 400);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null); // Center window
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameMenu();
    }
}
