package org.gui;

import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Auth extends JFrame implements ActionListener {
    public String getUserText() {
        return userText;
    }

    public String getPwdText() {
        return pwdText;
    }

    private String userText;
    private String pwdText;
    Container container = getContentPane();
    JLabel userLabel = new JLabel("EMAIL");
    JLabel welcomeLabel = new JLabel("Welcome back! enter your credentials!");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");

    public Auth() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        welcomeLabel.setBounds(300, 50, 300, 30);
        userLabel.setBounds(300, 150, 100, 30);
        passwordLabel.setBounds(300, 220, 100, 30);
        userTextField.setBounds(400, 150, 150, 30);
        passwordField.setBounds(400, 220, 150, 30);
        showPassword.setBounds(400, 250, 150, 30);
        loginButton.setBounds(300, 300, 100, 30);
        resetButton.setBounds(450, 300, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(welcomeLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);

        // Add a window listener to handle the close operation
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Exit the application when the window is closed
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());

        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

    }

    public void informUser(User user) {
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }
}