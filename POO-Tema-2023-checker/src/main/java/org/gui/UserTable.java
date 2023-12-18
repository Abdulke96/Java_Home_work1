package org.gui;

import org.example.IMDB;
import org.example.User;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserTable extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserTable(List<User> userList) {
        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("User Table Example");

        // Set up the table model with column names
        String[] columnNames = {"Field", "Value"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create the table and set the model
        userTable = new JTable(tableModel);

        // Add a scroll pane to the table
        JScrollPane scrollPane = new JScrollPane(userTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create a JMenu
        JMenu userMenu = new JMenu("Users");

        // Populate the JMenu with JMenuItem for each user
        for (User user : userList) {
            JMenuItem userMenuItem = new JMenuItem(user.getUsername().toString());
            userMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Show the user details in a JPopupMenu
                    showUserDetailsPopup(user);
                }
            });
            userMenu.add(userMenuItem);
        }

        // Add the JMenu to the menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(userMenu);
        setJMenuBar(menuBar);

        // Display the frame
        setVisible(true);
    }

    private void showUserDetailsPopup(User user) {
        // Clear the table model
        tableModel.setRowCount(0);

        // Populate the table model with user details
        tableModel.addRow(new Object[]{"Username", user.getUsername().toString()});
        tableModel.addRow(new Object[]{"Email", user.getEmail().toString()});
       // tableModel.addRow(new Object[]{"Country", user.getInformation().getCountry()});
        tableModel.addRow(new Object[]{"Experience", user.getExperience()});

        // Create a JPopupMenu and show it
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JScrollPane(userTable));
        popupMenu.show(this, getWidth() / 2, getHeight() / 2);
    }

//    public static void main(String[] args) {
//        // Create a list of User instances (replace this with your actual list)
//        List<User> userList = (List<User>) IMDB.getInstance().getCurrentUser();
//
//        // Create and display the table
//        SwingUtilities.invokeLater(() -> new UserTable(userList));
//    }
}
