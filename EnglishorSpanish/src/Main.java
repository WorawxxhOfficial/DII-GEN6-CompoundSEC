import gui.KeyCardGUI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            KeyCardGUI gui = new KeyCardGUI();
            gui.setVisible(true);
        });
    }
}
