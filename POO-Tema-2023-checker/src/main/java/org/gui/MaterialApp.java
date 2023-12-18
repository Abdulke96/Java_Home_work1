package org.gui;

import org.example.IMDB;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.security.PublicKey;

public class MaterialApp extends JFrame {
    private final User<?> currentUser;
    private JLabel mainPhotoLabel;
    private JLabel smallPhotolabel;

    private int currentPhotoIndex = 1;
    public MaterialApp( User<?> currentUser) {
        this.currentUser =  currentUser;

    }

    private Dimension findScreenSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }
    public void ImdbDisplay() {
        setSize(findScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setVisible(true);
    }
    public JMenu imdbMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.add(new JMenuItem("File"));
        menu.add(new JMenuItem("Exit"));
        menu.setBackground(Color.RED);
        return menu;
    }
    public JPanel userAccount() {
        ImageIcon icon = new ImageIcon("./imdb.png");
        JPanel panel = new JPanel();
        panel.add(new JLabel(icon));
        panel.setBackground(Color.ORANGE);
        panel.setPreferredSize(new Dimension(200, 200));
        panel.add(new JLabel("User Account"));
        return panel;
    }
    public JTextField imdbSearch() {
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(600, 30));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setFont(new Font("Arial", Font.BOLD, 20));
        searchField.setText("Search");
        return searchField;

    }
    public JPanel imdbPanel(JPanel panel, int width, int height) {

        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(Color.ORANGE);
        panel.add(imdbSearch());
        // add search button
        JButton searchButton = new JButton("Search");
        panel.add(searchButton);

        // add JComboBox
        String[] options = {"Actor", "Film", "Settings"};
        JComboBox comboBox = new JComboBox(options);
        panel.add(comboBox);
        return panel;
    }
    public JMenuBar imdbMenuBar( JMenuBar menuBar, JMenu menu, int width, int height) {

        menuBar.add(menu);
        menuBar.setPreferredSize(new Dimension(getWidth(), 120));
        menuBar.setBackground(Color.ORANGE);
        menuBar.setSize(width, height);

        return menuBar;
    }
    public void ImdbAppBar(String text) {
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = imdbMenu("IMDb App");
        JMenu action = imdbMenu("Action");
        JPanel searchField = imdbPanel(new JPanel(), 600, 120);
        menuBar = imdbMenuBar(menuBar, mainMenu, 200, 300);
        menuBar = imdbMenuBar(menuBar, action, 800, 900);
        menuBar.add(searchField);
        menuBar.add(userAccount());

        setJMenuBar(menuBar);


    }
    public JPanel ImdbContent( Color color, int width, int height, String text) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(width, height));
        panel.add(new JLabel(text));
        return panel;
    }
    public void ImdbHome() {
        JPanel panel =  ImdbContent( Color.ORANGE, 200, 200, "Home");
        JPanel panel2 = ImdbContent(Color.RED, 400, 400, "le");
        add(panel);
        add(panel2);
    }
}
class myApp {
    public static void main(String[] args) {
        User<?> user = IMDB.getInstance().getCurrentUser();
        MaterialApp app = new MaterialApp( user);
        app.ImdbDisplay();
        app.ImdbAppBar("IMDb App");
        app.imdbMenu("File");
        app.ImdbHome();


    }
}
