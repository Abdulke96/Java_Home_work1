//package org.gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class MultiScreenApp {
//    private JFrame frame;
//    private JPanel currentPanel;
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            new MultiScreenApp().startApp();
//        });
//    }
//
//    private void startApp() {
//        frame = new JFrame("Multi-Screen App");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//        frame.setLocationRelativeTo(null);
//
//        showLoginScreen();
//
//        frame.setVisible(true);
//    }
//
//    private void showLoginScreen() {
//        JPanel loginPanel = new JPanel(new BorderLayout());
//
//        JLabel label = new JLabel("Login Screen");
//        label.setHorizontalAlignment(JLabel.CENTER);
//
//        JButton loginButton = new JButton("Login");
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showMainScreen();
//            }
//        });
//
//        loginPanel.add(label, BorderLayout.CENTER);
//        loginPanel.add(loginButton, BorderLayout.SOUTH);
//
//        setCurrentPanel(loginPanel);
//    }
//
//    private void showMainScreen() {
//        JPanel mainPanel = new JPanel(new BorderLayout());
//
//        JLabel label = new JLabel("Main Screen");
//        label.setHorizontalAlignment(JLabel.CENTER);
//
//        JButton settingsButton = new JButton("Settings");
//        settingsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showSettingsScreen();
//            }
//        });
//
//        JButton backButton = new JButton("Back to Login");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showLoginScreen();
//            }
//        });
//
//        mainPanel.add(label, BorderLayout.CENTER);
//        mainPanel.add(settingsButton, BorderLayout.NORTH);
//        mainPanel.add(backButton, BorderLayout.SOUTH);
//
//        setCurrentPanel(mainPanel);
//    }
//
//    private void showSettingsScreen() {
//        JPanel settingsPanel = new JPanel(new BorderLayout());
//
//        JLabel label = new JLabel("Settings Screen");
//        label.setHorizontalAlignment(JLabel.CENTER);
//
//        JButton backButton = new JButton("Back to Main");
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showMainScreen();
//            }
//        });
//
//        settingsPanel.add(label, BorderLayout.CENTER);
//        settingsPanel.add(backButton, BorderLayout.SOUTH);
//
//        setCurrentPanel(settingsPanel);
//    }
//
//    private void setCurrentPanel(JPanel panel) {
//        if (currentPanel != null) {
//            frame.remove(currentPanel);
//        }
//        currentPanel = panel;
//        frame.add(currentPanel);
//        frame.revalidate();
//        frame.repaint();
//    }
//}
