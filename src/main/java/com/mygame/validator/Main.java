package com.mygame.validator;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use Swing's default look and feel if the system one is unavailable.
            }

            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}
