package org.constants;

import org.example.*;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class GuiConstants extends JFrame {
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
public static Integer[] getDayList() {
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        return days;
    }
  public static Integer[] getYearList() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }
        return years;
    }
    public static Integer[] getAgeList(){
        Integer[] ages = new Integer[120];
        for (int i = 0; i<120; i++){
            ages[i] = i+1;
        }
        return ages;
    }

public static JComboBox<Integer> createComboBox(Integer[] items) {
        return new JComboBox<>(items);
    }
    public static int selectRealeaseYear(String message) {
        Integer[] years = getYearList();
        JComboBox<Integer> comboBox = createComboBox(years);

        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                message,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? (int) comboBox.getSelectedItem() : -1;

    }

    public static Production selectProduction(){
    List<String> productions = new ArrayList<>();
        for (Production production : IMDB.getInstance().getProductions()) {
            productions.add(production.getTitle());
        }

        JComboBox<String> comboBox = new JComboBox<>(productions.toArray(new String[0]));

        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Select a production",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? IMDB.getInstance().getProductions().get(comboBox.getSelectedIndex()) : null;
    }
    public static Actor selectActor(){
       List<String> actors = new ArrayList<>();
        for (Actor actor : IMDB.getInstance().getActors()) {
            actors.add(actor.getName());
        }

        JComboBox<String> comboBox = new JComboBox<>(actors.toArray(new String[0]));

        int result = JOptionPane.showOptionDialog(
                null,
                comboBox,
                "Select an actor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);
        return (result == JOptionPane.OK_OPTION) ? IMDB.getInstance().getActors().get(comboBox.getSelectedIndex()) : null;
    }
}
