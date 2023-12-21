package org.gui;

import javax.swing.*;
        import java.awt.*;
        import java.util.List;

public class LongStringListDisplay extends JFrame {

    public LongStringListDisplay(List<String> stringList) {
        setTitle("String List Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Make JTextArea non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 34)); // Set font for monospaced appearance
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Append each string to the JTextArea
        for (String str : stringList) {
            textArea.append("       "+str + "\n");
        }

        add(scrollPane, BorderLayout.CENTER);

        // Set the size to fill the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> {
            List<String> longStringList = createLongStringList();
            new LongStringListDisplay(longStringList);
        });
    }

    // Example method to generate a list of long strings
    private static List<String> createLongStringList() {
        return List.of(
                "Very long string 1...",
                "Very long string 2...",
                "Very long string 3...",
                "1) View productions details",
                "2) View actors details", // for all
                "3) View notifications", // for all
                "4) Search for actors/movies/series", // for all
                "5) Add/Delete actors/movies/series to/from favorites",
                "6) Add/Delete user",
                "9) Add/Delete actor/movie/series/ from system",
                "10) Update movie details", // for admin and contributor
                "11) Update actor details", // for admin and contributor
                "12) Solve requests",// for admin and contributor
                "13) Logout",
                "14) Exit",
                "Very long string 1...",
                "Very long string 2...",
                "Very long string 3...",
                "1) View productions details",
                "2) View actors details", // for all
                "3) View notifications", // for all
                "4) Search for actors/movies/series", // for all
                "5) Add/Delete actors/movies/series to/from favorites",
                "6) Add/Delete user",
                "9) Add/Delete actor/movie/series/ from system",
                "10) Update movie details", // for admin and contributor
                "11) Update actor details", // for admin and contributor
                "12) Solve requests",// for admin and contributor
                "13) Logout",
                "14) Exit",
                "Very long string 1...",
                "Very long string 2...",
                "Very long string 3...",
                "1) View productions details",
                "2) View actors details", // for all
                "3) View notifications", // for all
                "4) Search for actors/movies/series", // for all
                "5) Add/Delete actors/movies/series to/from favorites",
                "6) Add/Delete user",
                "9) Add/Delete actor/movie/series/ from system",
                "10) Update movie details", // for admin and contributor
                "11) Update actor details", // for admin and contributor
                "12) Solve requests",// for admin and contributor
                "13) Logout",
                "14) Exit",
                "Very long string 1...",
                "Very long string 2...",
                "Very long string 3...",
                "1) View productions details",
                "2) View actors details", // for all
                "3) View notifications", // for all
                "4) Search for actors/movies/series", // for all
                "5) Add/Delete actors/movies/series to/from favorites",
                "6) Add/Delete user",
                "9) Add/Delete actor/movie/series/ from system",
                "10) Update movie details", // for admin and contributor
                "11) Update actor details", // for admin and contributor
                "12) Solve requests",// for admin and contributor
                "13) Logout",
                "14) Exit"
                // ... (your list of long strings)
        );
    }
}
