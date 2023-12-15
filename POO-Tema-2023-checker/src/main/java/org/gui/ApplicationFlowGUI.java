package org.gui;
import org.example.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationFlowGUI extends JFrame {
    private final User<?> currentUser;

    public ApplicationFlowGUI(User<?> currentUser) {
        this.currentUser = currentUser;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("IMDB APP");
        setBackground(Color.gray);
        setForeground(Color.black);
        setSize(screenSize.width , screenSize.height );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        JLabel welcomeLabel = new JLabel("Welcome back user " + "!" + currentUser.getEmail() + "!");
        JLabel usernameLabel = new JLabel("Username: " + currentUser.getUsername());
        JLabel experienceLabel = new JLabel("User experience: " + currentUser.getExperience());
        JLabel actionLabel = new JLabel();

        panel.add(welcomeLabel);
        panel.add(usernameLabel);
        panel.add(experienceLabel);
        panel.add(actionLabel);

        String[] actions = {
                "View productions details",
                "View actors details",
                "View notifications",
                "Search for actors/movies/series",
                "Add/Delete actors/movies/series to/from favorites",
                "Add/Delete user",
                "Add/Delete actor/movie/series/ from system",
                "Update movie details",
                "Update actor details",
                "Solve requests",
                "Logout"
        };

        for (int i = 0; i < actions.length; i++) {
            JButton button = new JButton(actions[i]);
            button.setBackground(Color.pink);
            button.addActionListener(new ActionHandler(i + 1));
            panel.add(button);
        }

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private class ActionHandler implements ActionListener {
        private final int choice;

        public ActionHandler(int choice) {
            this.choice = choice;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            performAction(choice);
        }
    }

    private void performAction(int choice) {
        switch (choice) {
            case 1:
               viewProductionDetails();
                break;
                case 2:
                viewActorDetails();
                break;
            case 3:
                viewNotifications();
                break;
            case 4:
                search();
                break;
            case 5:
                addDeleteFavorites();
                break;
            case 6:
                addDeleteUser();
                break;
            case 7:
                addDeleteProductionSystem();
                break;
            case 8:
                updateMovieDetails();
                break;
            case 9:
                updateActorDetails();
                break;
            case 10:
                solveRequests();
                break;

            case 11:
                dispose();
                // Example: new Auth();
                break;
            default:
                System.out.println("Action not implemented yet");
        }
    }
    public void viewProductionDetails(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Your Production details");
        getDimension(screenSize);


    }

    public void viewActorDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Your Actor details");
        setForeground(Color.black);
        setBackground(Color.gray);
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    private void getDimension(Dimension screenSize) {
        setBackground(Color.gray);
        setForeground(Color.black);
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        for (String pro : currentUser.getProductionsContribution()) {
            JLabel productionLabel = new JLabel(pro);
            panel.add(productionLabel);

        }
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void viewNotifications() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Your Notifications");
        getDimension(screenSize);

    }

    public void search() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Search for actors/movies/series");
        getDimension(screenSize);

    }

    public void addDeleteFavorites() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Add/Delete actors/movies/series to/from favorites");
        getDimension(screenSize);

    }

    public void addDeleteUser() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Add/Delete user");
        getDimension(screenSize);

    }

    public void addDeleteProductionSystem() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        setTitle("Add/Delete actor/movie/series/ from system");
        getDimension(screenSize);

    }

    public void updateMovieDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        getDimension(screenSize);

    }

    public void updateActorDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;

        getDimension(screenSize);

    }

    public void solveRequests() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;

        getDimension(screenSize);

    }

    public void logout() {
        System.out.println("Regular user logged out.");
    }




}
