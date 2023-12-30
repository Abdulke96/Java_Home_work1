package org.helper;

import javax.swing.*;
import java.awt.*;

public class SpacingExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Spacing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 200)); // Set horizontal and vertical gap

        JLabel label1 = new JLabel("Label 1");
        JButton button1 = new JButton("Button 1");
        JLabel label2 = new JLabel("Label 2");
        JButton button2 = new JButton("Button 2");

        mainPanel.add(label1);
        mainPanel.add(button1);
        mainPanel.add(label2);
        mainPanel.add(button2);

        frame.getContentPane().add(mainPanel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
