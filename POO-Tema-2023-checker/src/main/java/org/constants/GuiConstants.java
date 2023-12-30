package org.constants;

import org.example.AccountType;
import org.example.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class GuiConstants {
    public static List<String> contributor = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites", "Create/DeleteRequest", "Add/Delete actor/movie/series/ from system", "Update productions details", "Update actor details", "Solve requests", "Logout", "Exit");
    public static  List<String> admin = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites", "Add/Delete user", "Add/Delete actor/movie/series/ from system", "Update productions details", "Update actor details", "Solve requests", "Logout", "Exit");
    public static  List<String> regular = List.of("home ", "View productions details", "View actors details", "View notifications", "Search for actors/movies/series", "Add/Delete actors/movies/series to/from favorites","Create/DeleteRequest", "Add / Delete Review", "Logout", "Exit");

    // GUI frequently used functions
    public static ImageIcon getIcon(String name, int width, int height){
        ImageIcon originalIcon = new ImageIcon(Constants.path + name);
        return new ImageIcon(getScaledImage(originalIcon.getImage(), width, height));
    }
   public static Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
   public static AccountType convertToAccountType(int userType) {
       return switch (userType) {
           case 0 -> AccountType.Admin;
           case 1 -> AccountType.Contributor;
           case 2 -> AccountType.Regular;
           default -> throw new IllegalStateException("Unexpected value: " + userType);
       };
    }

    public static boolean isFullName(String name) {
        return name.matches("^[a-zA-Z\\s]*$");
    }
    public static String showInputDialog(String instruction, String preFilledValue, int width, int height) {

        JTextArea inputArea = new JTextArea(preFilledValue);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 20));
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(inputArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(width, height));
        Object[] message = { instruction, scrollPane };

        int option = JOptionPane.showConfirmDialog(null, message, "Input", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {

            return inputArea.getText();
        } else {
            return null;
        }
    }
    public static String breakString(String input, int lettersPerLine) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i += lettersPerLine) {
            int end = Math.min(i + lettersPerLine, input.length());
            result.append(input, i, end);
            result.append("\n");
        }

        return result.toString();
    }
    public static String cutString(String inputString, int numberOfLetters) {
        StringBuilder resultBuilder = new StringBuilder();
        if (inputString.length()<numberOfLetters) {
            return inputString;
        }

        for (int i = 0; i < numberOfLetters; i++) {
            char currentChar = inputString.charAt(i);
            resultBuilder.append(currentChar);
        }
        resultBuilder.append("...");

        return resultBuilder.toString();
    }

    public static String chooseType(String message, String option1, String option2) {
        String[] options = {option1, option2};

        JComboBox<String> comboBox = new JComboBox<>(options);
        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                message,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? (String) comboBox.getSelectedItem() : "";
    }
    public static Genre selectGenre(String message) {
        String[] genreArray = new String[Genre.values().length];
        for (int i = 0; i < Genre.values().length; i++) {
            genreArray[i] = Genre.values()[i].name();
        }

        JComboBox<String> comboBox = new JComboBox<>(genreArray);

        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                message,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? Genre.valueOf((String) comboBox.getSelectedItem()) : null;
    }
    public static int selectRating(String message) {
        String[] ratingArray = new String[10];
        for (int i = 0; i < 10; i++) {
            ratingArray[i] = String.valueOf(i+1);
        }

        JComboBox<String> comboBox = new JComboBox<>(ratingArray);

        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                message,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? Integer.parseInt((String) Objects.requireNonNull(comboBox.getSelectedItem())) : -1;
    }
}
