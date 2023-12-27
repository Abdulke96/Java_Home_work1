package org.gui;

import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FancySearchBar extends JFrame {
    private JTextField searchBar;
    private JButton searchButton;

    public FancySearchBar() {
        super("Fancy Search Bar");

        // Set layout manager
        setLayout(new FlowLayout());

        // Create search bar
        searchBar = new JTextField(40);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create search button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchBar.getText();
                // Add your search logic here
                JOptionPane.showMessageDialog(FancySearchBar.this, "Searching for: " + searchText);
            }
        });

        // Add components to the frame
        add(searchBar);
        add(searchButton);

        // Set frame properties
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI on the EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FancySearchBar();
            }
        });
    }
}
