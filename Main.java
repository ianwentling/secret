import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Start the Game Menu instead of the game directly
            new GameMenu();
        });
    }
}
