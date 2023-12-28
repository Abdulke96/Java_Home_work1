package org.gui;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InputDialog {

    public static String showInputDialog(String instruction, String preFilledValue, int width, int height) {
        // Create a JTextArea with pre-filled value
        JTextArea inputArea = new JTextArea(preFilledValue);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        // Create a JScrollPane to allow scrolling if the content is too large
        JScrollPane scrollPane = new JScrollPane(inputArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(width, height));

        // Create an array of objects to represent the components in the dialog
        Object[] message = { instruction, scrollPane };

        // Show the input dialog
        int option = JOptionPane.showConfirmDialog(null, message, "Input", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked OK
        if (option == JOptionPane.OK_OPTION) {
            // Return the text entered by the user
            return inputArea.getText();
        } else {
            // Return null if the user canceled the input
            return null;
        }
    }

    // Example usage:
    public static void main(String[] args) {
        String instruction = "Enter something:";
        String preFilledValue = "Default Value";

        String userInput = showInputDialog(instruction, preFilledValue, 300, 200);

        if (userInput != null) {
            System.out.println("User entered: " + userInput);
        } else {
            System.out.println("User canceled the input.");
        }
    }
}
