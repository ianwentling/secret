import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu {
    private JFrame frame;
    private JFrame gameFrame;
    private JFrame characterFrame;
    private JLabel difficultyLabel;

    public GameMenu() {
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(5, 1)); // Increased grid rows for the new label
        frame.setLocationRelativeTo(null); // Centers the window on the screen

        // Create a label to display the selected difficulty
        difficultyLabel = new JLabel("Select Game Difficulty", SwingConstants.CENTER);
        frame.add(difficultyLabel);

        // Create difficulty buttons
        JButton easyButton = new JButton("Easy");
        easyButton.setBackground(Color.GREEN);
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEasyMode();
            }

        });

        JButton mediumButton = new JButton("Medium");
        mediumButton.setBackground(Color.YELLOW);
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMediumMode();
            }
        });

        JButton hardButton = new JButton("Hard");
        hardButton.setBackground(Color.RED);
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHardMode();
            }
        });

        JButton characterInfoButton = new JButton("Character Info");
        characterInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCharacterInfo();
            }
        });

        // Add the buttons to the frame
        frame.add(easyButton);
        frame.add(mediumButton);
        frame.add(hardButton);
        frame.add(characterInfoButton);

        frame.setVisible(true);
    }

    private void openEasyMode() {
//        gameFrame = new JFrame("Easy Mode");
//        gameFrame.setSize(500, 500);
//        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gameFrame.setLocationRelativeTo(null); // Centers the window
//
//
//        JPanel easyPanel = new JPanel();
//        easyPanel.setLayout(new BorderLayout());
//        easyPanel.add(new JLabel("Easy Mode Content", SwingConstants.CENTER), BorderLayout.CENTER);

//        JButton backButton = new JButton("Back to Menu");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameFrame.dispose();
//                frame.setVisible(true);
//
//            }
//        });

        frame.setVisible(false);// Hides main menu
        new EasyMode().setVisible(true);
//        easyPanel.add(backButton, BorderLayout.SOUTH);
//        gameFrame.add(easyPanel);
//        gameFrame.setVisible(true);
//        frame.setVisible(false);
    }

    private void openMediumMode() {
//        gameFrame = new JFrame("Medium Mode");
//        gameFrame.setSize(500, 500);
//        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gameFrame.setLocationRelativeTo(null); // Centers the window
//
//        JPanel mediumPanel = new JPanel();
//        mediumPanel.setLayout(new BorderLayout());
//        mediumPanel.add(new JLabel("Medium Mode Content", SwingConstants.CENTER), BorderLayout.CENTER);
//
//        JButton backButton = new JButton("Back to Menu");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameFrame.dispose();
//                frame.setVisible(true);
//            }
//        });
//
//        mediumPanel.add(backButton, BorderLayout.SOUTH);
//        gameFrame.add(mediumPanel);
//        gameFrame.setVisible(true);
//        frame.setVisible(false);
        frame.setVisible(false);
        new MediumMode().setVisible(true);
    }

    private void openHardMode() {
//        gameFrame = new JFrame("Hard Mode");
//        gameFrame.setSize(500, 500);
//        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gameFrame.setLocationRelativeTo(null); // Centers the window
//
//        JPanel hardPanel = new JPanel();
//        hardPanel.setLayout(new BorderLayout());
//        hardPanel.add(new JLabel("Hard Mode Content", SwingConstants.CENTER), BorderLayout.CENTER);
//
//        JButton backButton = new JButton("Back to Menu");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                gameFrame.dispose();
//                frame.setVisible(true);
//            }
//        });
//
//        hardPanel.add(backButton, BorderLayout.SOUTH);
//        gameFrame.add(hardPanel);
//        gameFrame.setVisible(true);
//        frame.setVisible(false);
        frame.setVisible(false);
        new HardMode().setVisible(true);
    }

    private void openCharacterInfo() {
        characterFrame = new JFrame("Character Info");
        characterFrame.setSize(500, 500);
        characterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        characterFrame.setLocationRelativeTo(null); // Centers the window

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(new JLabel("Character Info Content", SwingConstants.CENTER), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                characterFrame.dispose();
                frame.setVisible(true);
            }
        });

        infoPanel.add(backButton, BorderLayout.SOUTH);
        characterFrame.add(infoPanel);
        characterFrame.setVisible(true);
        frame.setVisible(false);
    }

    public static void main(String[] args) {
        new GameMenu();
    }
}

