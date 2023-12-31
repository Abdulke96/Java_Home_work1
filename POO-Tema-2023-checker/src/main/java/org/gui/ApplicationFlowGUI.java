package org.gui;

import org.constants.Constants;
import org.constants.FunctionsFactory;
import org.constants.GuiConstants;
import org.example.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class ApplicationFlowGUI extends JFrame {
    private User<?> currentUser; // to track the current user

    // lists of users, actors, productions, requests
    List<Actor> actors = IMDB.getInstance().getActors();
    List<Production> productions = IMDB.getInstance().getProductions();
    List<User<?>> users = IMDB.getInstance().getUsers();
    List<Request> requests = IMDB.getInstance().getRequests();

    private JLabel photoLabel; // fancy photo display
    private int currentPhotoIndex = 1; // to track the current photo index
    private JPanel currentPanel; //to track the current panel
    JPanel mainScreenPanel = new JPanel(new GridLayout(1, 2)); // the main screen panel for the home menu
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // to get full screen size of the device
    JComboBox<String> deleteActorOrProductionComboBox; // combo box for the delete actor/production
    JComboBox<String> addaActorOrProductionComboBox;// combo box for the add  actor/production

    public ApplicationFlowGUI(User<?> currentUser) {
        /*
          The constructor of the ApplicationFlowGUI class.
          It has one parameter:
          @param currentUser: User<?>
         * It initializes the fields and calls the initializeUI method.
         */
        this.currentUser = currentUser;
        initializeUI();
    }

    private void initializeUI() {

        configureFrame();
        createMenu();
        createPhotoDisplayAndButtons();
        schedulePhotoChange();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public ApplicationFlowGUI(){
        /*
             The constructor for the login screen.
         */
         configureFrame();
         setVisible(true);
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        JPanel topPanel = new JPanel();

        JLabel welcomeLabel = ImdbLabel("WELCOME TO IMDB");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 80));
        topPanel.add(welcomeLabel);
        loginPanel.add(topPanel);
        JLabel userLabel = ImdbLabel("Email: ");
        JLabel passwordLabel = ImdbLabel("Password:");
        JCheckBox showPassword = new  JCheckBox("Show Password");
        JButton loginButton = ImdbButton("Login", 40);
        JButton resetButton = ImdbButton("Reset", 40);

        JTextField userTextField =  new JTextField(20);
        userTextField.setFont(new Font("Arial", Font. BOLD, 40));
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.BOLD, 40));
        loginButton.addActionListener(e -> {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());
            try {
                this.currentUser = authenticate(userText, pwdText);
                if (this.currentUser != null) {
                     setVisible(false);
                    new ApplicationFlowGUI(currentUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Authentication failed. Retry.");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error during authentication: " + exception.getMessage());
            }
        });
        resetButton.addActionListener(e -> {
            //reset the text fields
            userTextField.setText("");
            passwordField.setText("");
        });
        showPassword.addActionListener(e -> {
            //show the password if the checkbox is selected
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.add(Box.createRigidArea(new Dimension(0, 20)));

         middlePanel.add(ImdbInputPanelFlow(userLabel, userTextField));
         middlePanel.add(ImdbInputPanelFlow(passwordLabel, passwordField));
         middlePanel.add(showPassword);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
         buttonPanel.add(resetButton);
         middlePanel.add(buttonPanel);
         loginPanel.add(middlePanel);
         JPanel bottomPanel = new JPanel();
         // display IMDB icon on the login screen
         JLabel iconLabel = new JLabel(GuiConstants.getIcon("imdbicon.png", 300, 300));
            bottomPanel.add(iconLabel);
         loginPanel.add(bottomPanel);
        setCurrentPanel(loginPanel);
    }

    /**
     * The configureFrame method is used to configure the frame.
     * it sets the title, size and default close operation.
     */
    private void configureFrame() {
        setTitle("IMDB APP");
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * The createMenu method is used to create the menu bar.
     * It has no parameters.
     * It creates the menu bar and adds the menus to it.
     * It also adds the action listeners to the menu items.
     * It sets the menu bar to the frame.
     */
    private void createMenu() {
        JPanel menuPanel = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JPanel searchPanel = new JPanel(new FlowLayout());
        JMenu homeMenu = homeMenu();
        menuBar.add(homeMenu);
        JTextField searchField = ImdbTextField(0,40);
        menuPanel.add(searchField);

        JButton button = new JButton(GuiConstants.getIcon("search.png", 100, 100) );
        menuPanel.add(button);
        button.addActionListener(e->{
            String search = searchField.getText();
            searchField.setText("");
            searchItem(search, searchPanel);
            setCurrentPanel(searchPanel);

        });

        JMenu actionsMenu = createActionsMenu();
        menuBar.add(actionsMenu);
        menuBar.add(searchField);
        menuBar.add(button);


        JMenu accountMenu = displayUserAccount(currentUser.getUsername());
        accountMenu.setFont(new Font("Arial", Font.BOLD, 40));
        menuBar.add(accountMenu);
        menuBar.setBackground(Color.ORANGE);
        menuBar.setBorderPainted(true);
        menuBar.setFont(new Font("Arial", Font.BOLD, 60));

        setJMenuBar(menuBar);
    }

    /**
     * The createActionsMenu method is used to create the actions' menu.
     * it calls the showMenu method to create the actions' menu.
     */
    private JMenu createActionsMenu() {
        JMenu actionsMenu = new JMenu();
        actionsMenu.addSeparator();
        actionsMenu.setMenuLocation(10, 10);
        actionsMenu.setFont(new Font("Arial", Font.BOLD, 40));
        actionsMenu.setIcon(GuiConstants.getIcon("imdbicon.png", 100, 100));
        List<String> actions = List.of("");
        return showMenu(actionsMenu, actions);
    }
    /**
     * The homeMenu method is used to create the home menu.
     * it calls the showMenu method to create the home menu.
     */
    private JMenu homeMenu() {
        JMenu homeMenu = new JMenu("Home");
        homeMenu.addSeparator();
        homeMenu.setMenuLocation(10, 10);
        homeMenu.setFont(new Font("Arial", Font.BOLD, 80));
        homeMenu.setIcon(GuiConstants.getIcon("home.png", 60, 60));
        List<String> actions = getActions();
        return showMenu(homeMenu, actions);
    }

    /**
     * The getActions method is used to get the actions for the current user.
     * It returns a list of strings containing the actions for the current user.
     */
    @NotNull
    private List<String> getActions() {
        List<String> actions = new ArrayList<>();
        if (currentUser instanceof Admin) {
            actions = GuiConstants.admin;
        } else if (currentUser instanceof Contributor) {
            actions = GuiConstants.contributor;
        } else if (currentUser instanceof Regular) {
            actions = GuiConstants.regular;
        }
        return actions;
    }

    /**
     * The displayUserAccount method is used to display the user account.
     * It has one parameter:
     */
    private JMenu displayUserAccount(String name) {
        JMenu accountMenu = new JMenu(name);
        accountMenu.setIcon(GuiConstants.getIcon("account.png", 60, 60));
        List<String> info = Constants.userInfo(currentUser);
        return showAccountMenu(accountMenu, info);
    }
    /**
     * The showAccountMenu method is used to display the user account.
     * It has two parameters:
     * @param accountMenu: JMenu
     * @param info: List<String>
     * It returns the account menu.
     */
    private JMenu showAccountMenu( JMenu accountMenu, List<String> info){
        for (String s : info) {
            JMenuItem action = new JMenuItem(s);
            action.setFont(new Font("Arial", Font.BOLD, 24));
            accountMenu.add(action);
        }

        return accountMenu;

    }
    /**
     * The showMenu method is used to create  a menu checking the user type.
     * It has two parameters:
     * @param accountMenu: JMenu
     * @param info: List<String>
     * It returns the menu.
     */
    private JMenu showMenu(JMenu accountMenu, List<String> info) {
        for (int i = 0; i < info.size(); i++) {
            JMenuItem action = new JMenuItem(info.get(i));
            action.setFont(new Font("Arial", Font.BOLD, 40));
            int finalI = i;
            action.addActionListener(e -> {
                if (currentUser instanceof Admin) {
                    adminAction(finalI);
                } else if (currentUser instanceof Contributor) {
                    contributorAction(finalI);
                } else if (currentUser instanceof Regular) {
                    regularAction(finalI);
                }
            });
            accountMenu.add(action);
        }

        return accountMenu;
    }

    /**
     * The  createPhotoDisplayAndButtons method is used to create the photo display and the buttons.
     *  it calls the loadPhoto method to load the photo.
     * it is responsible for decorating the main screen panel.
     *
     */

    private void createPhotoDisplayAndButtons() {
        JPanel changingPhotoPanel = new JPanel();
        JPanel scrollPanel = new JPanel();
        JPanel photoPanels;
        photoLabel = new JLabel();
        photoPanels = new JPanel(new BorderLayout());
        photoPanels.setSize(3*screenSize.width/4, screenSize.height);

        JLabel recommendation = recommendationLabel();

        photoPanels.add(recommendation, BorderLayout.NORTH);
        photoPanels.setLayout(new BoxLayout(photoPanels, BoxLayout.Y_AXIS));
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Sort by rating", "Sort by name", "sort by average rating" });
        sortOptions.setFont(new Font("Arial", Font.BOLD, 40));
        sortOptions.addActionListener(e -> {
            sortProduct(scrollPanel, sortOptions);
            sorterFunction(scrollPanel);
            scrollPanel.revalidate();
            scrollPanel.repaint();
        });
        photoPanels.add(sortOptions, BorderLayout.NORTH);

        sorterFunction(scrollPanel);
        photoPanels.add(new JScrollPane(scrollPanel), BorderLayout.CENTER);
        photoPanels.add(Box.createRigidArea(new Dimension(0, 20)));
        loadPhoto();

        JButton leftButton = new JButton(GuiConstants.getIcon("left_arrow.png", 60, 60));
        JButton rightButton = new JButton(GuiConstants.getIcon("right_arrow.png", 60, 60));
        Dimension imageSize = new Dimension(screenSize.width, screenSize.height);
        photoLabel.setPreferredSize(imageSize);
        changingPhotoPanel.add(photoLabel);
        leftButton.addActionListener(e -> showPreviousPhoto());
        rightButton.addActionListener(e -> showNextPhoto());

        mainScreenPanel.setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(leftButton, BorderLayout.WEST);
        buttonsPanel.add(Box.createHorizontalGlue()); // Creates space between leftButton and label

        JLabel label = new JLabel("WEL COME TO IMDB !!!");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setHorizontalAlignment(JLabel.CENTER);
        buttonsPanel.add(label, BorderLayout.CENTER);
        buttonsPanel.add(rightButton, BorderLayout.EAST);
        mainScreenPanel.add(changingPhotoPanel, BorderLayout.CENTER);
        mainScreenPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainScreenPanel.add(photoPanels, BorderLayout.LINE_END);
        setCurrentPanel(mainScreenPanel);
    }

    /**
     * the sortProduct method is used to sort the productions.
     * it has two parameters:
     *  @param scrollPanel: JPanel
     *  @param sortOptions: JComboBox<String>
     *
     */
    private void sortProduct(JPanel scrollPanel, JComboBox<String> sortOptions) {
        if (sortOptions.getSelectedIndex() == 0) {
            productions.sort((o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating()));
        } else if (sortOptions.getSelectedIndex() == 1){
            productions.sort(Comparator.comparing(Production::getTitle));
        } else if (sortOptions.getSelectedIndex() == 2){
            productions.sort((o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating()));
        }
        scrollPanel.removeAll();
    }
/**
     * the sorterFunction method is used to sort the productions.
     * it has one parameter:
     *  @param scrollPanel: JPanel
     *
     */
    private void sorterFunction(JPanel scrollPanel) {
        for (Production production : productions) {
                String duration = "";
                    Random random = new Random();
                    int currentPhotoIndex = random.nextInt(10) + 1;
                ImageIcon icon = new ImageIcon(Constants.path + "imdb" + currentPhotoIndex + ".png");
                if(production instanceof Movie){
                    duration = ((Movie) production).getDuration();
                } else if (production instanceof Series){
                    duration = ((Series) production).getNumSeasons() + " seasons";
                }
                JPanel recommend1 = recommendUser(icon,production.getTitle(), production.getPlot(), duration);
                scrollPanel.add(recommend1);
                scrollPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        }
    }

    /**
     * The recommendUser method is used to recommend a user a production.
     * It has four parameters:
     * @param icon: ImageIcon
     *  @param title: String
     * @param description: String
     * @param duration: String
     * It returns a panel containing the recommended production.
     * @return JPanel
     */

    private JPanel recommendUser(ImageIcon icon, String title, String description, String duration) {
        JPanel recommend = new JPanel(new BorderLayout());
        JPanel display = new JPanel(new BorderLayout());
        JPanel recommendationTable = new JPanel(new BorderLayout());
        JPanel photo = new JPanel(new BorderLayout());
        icon = new ImageIcon(GuiConstants.getScaledImage(icon.getImage(), 300, 300));
        photo.add(new JLabel(icon));
         JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        JPanel detail = new JPanel();
        detail.add(photo, BorderLayout.WEST);

        detail.setLayout(new FlowLayout());
        JLabel durationLabel = new JLabel(duration);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 24));
        boxPanel.add(durationLabel);
        JButton titleButton = new JButton(GuiConstants.cutString(title, 20));
        titleButton.setFont(new Font("Arial", Font.BOLD, 24));
        titleButton.addActionListener(e -> {
            for (Production production : productions) {
                if (production.getTitle().equals(title)) {
                    imdbTextArea(display, production.getTitle(), production.guiDisplay());
                }
            }
            setCurrentPanel(display);
        });

        boxPanel.add(titleButton, BorderLayout.WEST);
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descriptionLabel.setLineWrap(true);
        detail.add(boxPanel);
        recommendationTable.add(detail, BorderLayout.CENTER);
        recommend.add(recommendationTable, BorderLayout.WEST);


        return recommend;
    }

    private void schedulePhotoChange() {
        // Schedule photo change every 5 seconds
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showNextPhoto();
            }
        }, 5000, 5000);
    }

    private void loadPhoto() {
        // load  another photo from the resources  assets folder
        if (photoLabel.getWidth() > 0 && photoLabel.getHeight() > 0) {
            String photoPath = Constants.path + "imdb" + currentPhotoIndex + ".png";
            ImageIcon icon = new ImageIcon(photoPath);
            Image scaledImage = icon.getImage().getScaledInstance(photoLabel.getWidth() - 10, photoLabel.getHeight() - 10, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            photoLabel.setIcon(icon);
        }
    }

    private JLabel recommendationLabel() {
        // create a label for the recommendation
        JLabel recommendation = ImdbLabel("RECOMMENDATION");
        recommendation.setBackground(Color.ORANGE);
        recommendation.setForeground(Color.RED);
        return recommendation;
    }


    /**
     * The setCurrentPanel method is used to set the current panel.
     * It has one parameter:
     * @param panel: JPanel
     *it removes the current panel and adds a received panel.
     */
    private void setCurrentPanel(JPanel panel) {
        if (currentPanel != null) {
            getContentPane().removeAll();
        }
        currentPanel = panel;
        getContentPane().add(currentPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showNextPhoto() {
        // show the next photo by incrementing the current photo index
        currentPhotoIndex = (currentPhotoIndex % 10) + 1;
        loadPhoto();
    }

    private void showPreviousPhoto() {
        // show the previous photo by decrementing the current photo index
        currentPhotoIndex = (currentPhotoIndex - 2 + 10) % 10 + 1;
        loadPhoto();
    }

    /**
     * the contributionAction method is used to keep track of the contributor actions.
     * it has one parameter:
     *  @param action: int
     *
     */
    private void contributorAction(int action) {
        switch (action) {
            case 0 -> home();
            case 1 -> viewProductionDetails();
            case 2 -> viewActorDetails();
            case 3 -> viewNotifications();
            case 4 -> search();
            case 5 -> addDeleteFavorites();
            case 6 ->createDeleteRequest();
            case 7 -> addDeleteProductionOrActorFromSystem();
            case 8 -> updateMovieDetails();
            case 9 -> updateActorDetails();
            case 10 -> solveRequests();
            case 11 -> logout();
            case 12 -> exit();
        }
    }
    /**
     * the adminAction method is used to keep track of the admin actions.
     * it has one parameter:
     *  @param action: int
     *
     */
    private void adminAction(int action) {
        switch (action) {
            case 0 -> home();
            case 1 -> viewProductionDetails();
            case 2 -> viewActorDetails();
            case 3 -> viewNotifications();
            case 4 -> search();
            case 5 -> addDeleteFavorites();
            case 6 -> addDeleteUser();
            case 7 -> addDeleteProductionOrActorFromSystem();
            case 8 -> updateMovieDetails();
            case 9 -> updateActorDetails();
            case 10 -> solveRequests();
            case 11 -> logout();
            case 12 -> exit();
        }
    }
    /**
     * regularAction method is used to keep track of the regular actions.
     * it has one parameter:
     *  @param action: int

     */

    private void regularAction(int action) {
        switch (action) {
            case 0 -> home();
            case 1 -> viewProductionDetails();
            case 2 -> viewActorDetails();
            case 3 -> viewNotifications();
            case 4 -> search();
            case 5 -> addDeleteFavorites();
            case 6-> createDeleteRequest();
            case 7 -> AddDeleteReview();
            case 8 -> logout();
            case 9 -> exit();
        }
    }

    /**
     *  imdbTextArea method is used to create a text area.
     * it has three parameters:
     * @param detailsPanel: JPanel
     *                    @param title: String
     *                                @param details: String
     */
    private void imdbTextArea(JPanel detailsPanel, String title, String details) {
        JTextArea textArea = new JTextArea();
        textArea.setMargin(new Insets(40, 100, 40, 40));
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 34));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(details);
        JLabel titleLabel = ImdbLabel(title);
        JScrollPane scrollPane = new JScrollPane(textArea);
         scrollPane.add(titleLabel);
        detailsPanel.removeAll();
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    /**
     *viewProductionDetails method is used to view the production details.
     *
     */

    public void viewProductionDetails() {
        JPanel productionPanel = new JPanel(new GridLayout(1, 2));
        JPanel listPanel = new JPanel(new BorderLayout());
        JPanel detailsPanel = new JPanel(new BorderLayout());
         listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
         listPanel.setSize(screenSize.width/4, screenSize.height);

        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Sort by rating", "Sort by name", "sort by average rating" });
        sortOptions.setFont(new Font("Arial", Font.BOLD, 40));
        sortOptions.addActionListener(e -> {
            sortProduct(listPanel, sortOptions);
            productionPanel.removeAll();
            listPanel.add(sortOptions, BorderLayout.NORTH);
            // Add sorted buttons to listPanel
            for (Production production : productions) {
                JButton button = ImdbButton(production.getTitle(),40);
                listPanel.add(button);

                button.addActionListener(e1 -> imdbTextArea(detailsPanel, production.getTitle(), production.guiDisplay()));

                listPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            JScrollPane scrollPane = new JScrollPane(listPanel);
            productionPanel.add(scrollPane);
            productionPanel.add(detailsPanel);

            setCurrentPanel(productionPanel);

           productionPanel.revalidate();
              productionPanel.repaint();
        });

        listPanel.add(sortOptions, BorderLayout.NORTH);

        detailsPanel.setSize(screenSize.width*3/4, screenSize.height);
        for (Production production : productions) {
            JButton button = ImdbButton(production.getTitle(),40);
            listPanel.add(button);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            button.addActionListener(e -> imdbTextArea(detailsPanel, production.getTitle(), production.guiDisplay()));
        }


        JScrollPane scrollPane = new JScrollPane(listPanel);
        productionPanel.add(scrollPane);
        productionPanel.add(detailsPanel);
        setCurrentPanel(productionPanel);
    }
    /**
     *  viewActorDetails method is used to view the actor details.
     *
     */


    public void viewActorDetails() {
        JPanel actorPanel = new JPanel(new GridLayout(1, 2));
        JPanel listPanel = new JPanel(new BorderLayout());
        JPanel detailsPanel = new JPanel(new BorderLayout());
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setSize(screenSize.width/4, screenSize.height);
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Sort by name", "sort by performance" });
        sortOptions.setFont(new Font("Arial", Font.BOLD, 30));

        sortOptions.addActionListener(e -> {
            if (sortOptions.getSelectedIndex() == 0) {
                actors.sort(Comparator.comparing(Actor::getName));
            } else if (sortOptions.getSelectedIndex() == 1){
                actors.sort((o1, o2) -> Double.compare(o2.getNumberOfPerformances(), o1.getNumberOfPerformances()));
            }

            listPanel.removeAll();
            actorPanel.removeAll();
            listPanel.add(sortOptions, BorderLayout.SOUTH);

            for (Actor actor : actors) {
                JButton button = ImdbButton(actor.getName(), 40);
                listPanel.add(button);

                button.addActionListener(e1 -> imdbTextArea(detailsPanel, actor.getName(), actor.guiDisplay()));

                listPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            JScrollPane scrollPane = new JScrollPane(listPanel);
            actorPanel.add(scrollPane);
            actorPanel.add(detailsPanel);

            setCurrentPanel(actorPanel);
            actorPanel.revalidate();
            actorPanel.repaint();
        });

        listPanel.add(sortOptions, BorderLayout.NORTH);
        for (Actor actor : actors) {
            JButton button = ImdbButton(actor.getName(), 40);
            listPanel.add(button);
            listPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            button.addActionListener(e -> imdbTextArea(detailsPanel, actor.getName(), actor.guiDisplay()));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        actorPanel.add(scrollPane);
        actorPanel.add(detailsPanel);
        setCurrentPanel(actorPanel);
    }

    /**
     * viewNotifications method is used to view the notifications.
     */


    public void viewNotifications() {
        JPanel notificationsPanel = new JPanel(new BorderLayout());
        List<String> notifications = currentUser.getNotifications();
        JTextArea accountLabel = new JTextArea("\t\t\t\tName: "+currentUser.getName() + "\n" + "\t\t\t\tUser Name: "+ currentUser.getUsername() + "\n" +"\t\t\t\tUser Email: "+ currentUser.getEmail() + "\n" +"\t\t\t\tUser Type: "+ currentUser.getUserType());
        accountLabel.setEditable(false);
        accountLabel.setFont(new Font("Arial", Font.BOLD, 30));
        accountLabel.setLayout(new BorderLayout());
        accountLabel.add(Box.createRigidArea(new Dimension(0, 200)));
        notificationsPanel.add(accountLabel, BorderLayout.NORTH);
        StringBuilder notificationsString = new StringBuilder();
        notificationsString.append("\n\n\n").append("Notifications: ").append("\t");

        if (notifications.isEmpty()){
            notificationsString.append("No notifications");
        }
        else {
            for (String notification : notifications) {
                notificationsString.append(notification).append("\n");
            }
        }
        JTextArea notificationsArea = new JTextArea(GuiConstants.breakString(notificationsString.toString(),120));
        notificationsArea.setEditable(false);
        notificationsArea.setFont(new Font("Arial", Font.BOLD, 40));
        notificationsPanel.add(notificationsArea, BorderLayout.CENTER);
        setCurrentPanel(notificationsPanel);

    }

    /**
     * search method is used to search for a production or an actor.
     */

    public void search() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = ImdbTextField(40,40);
        JButton searchButton = ImdbButton("Search",40);

        searchButton.addActionListener(e -> {
            String search = searchField.getText();
            searchField.setText("");
            searchItem(search, searchPanel);

        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchPanel.setSize(400, 100);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchPanel, BorderLayout.CENTER);

        setCurrentPanel(panel);
    }


    private void searchItem(String search, JPanel searchPanel) {
        // helper method for the search method

        List<String> searchResults = new ArrayList<>();
        if (search.isEmpty()) {
            searchResults.add("No results found");
            dialogBox("empty search , please type something to search");
        }else{
            for (Actor actor : actors) {
                if (actor.getName().contains(search)) {
                    searchResults.add(actor.guiDisplay());
                }
            }

            for (Production production : productions) {
                if (production.getTitle().contains(search)) {
                    searchResults.add(production.guiDisplay());
                }
            }

            if (searchResults.isEmpty()) {
                searchResults.add("No results found");
            }
            searchPanel.setLayout(new BorderLayout());

            imdbTextArea(searchPanel, "Search results", String.join("\n", searchResults));
        }
    }

    /**
     * addDeleteFavorites method is used to add or delete a production or an actor from the favorites.
     */
    public void addDeleteFavorites() {


        JPanel favoritesPanel = new JPanel(new GridLayout(1, 2));
        JPanel addToFavoritePanel = new JPanel(new FlowLayout());
        JPanel deleteFromFavoritePanel = new JPanel(new FlowLayout());

        addToFavoritePanel.setLayout(new BoxLayout(addToFavoritePanel, BoxLayout.Y_AXIS));
        deleteFromFavoritePanel.setLayout(new BoxLayout(deleteFromFavoritePanel, BoxLayout.Y_AXIS));
        JLabel addToFavoriteLabel = ImdbLabel("ADD TO FAVORITES");
        addToFavoriteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToFavoritePanel.add(addToFavoriteLabel);

        deleteFromFavorite(deleteFromFavoritePanel);
        addToFavorite(addToFavoritePanel, deleteFromFavoritePanel);

        favoritesPanel.add(new JScrollPane(deleteFromFavoritePanel));
        favoritesPanel.add(new JScrollPane(addToFavoritePanel));

        setCurrentPanel(favoritesPanel);
    }

    private void addToFavorite(JPanel addToFavoritePanel, JPanel deleteFromFavoritePanel) {
        // helper method for the addDeleteFavorites method to add to favorites
        JLabel addToFavoriteLabel = ImdbLabel("Productions");
        addToFavoriteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToFavoritePanel.add(addToFavoriteLabel);
       for (Production production : productions) {
            JButton button = ImdbButton(production.getTitle(),40);
            addToFavoritePanel.add(button);
            addToFavoritePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            button.addActionListener(e -> {
                if (currentUser.getFavoriteProductions().contains(production.getTitle())){
                    dialogBox("already in favorites");
                } else {
                    currentUser.addToFavoriteProductions(production);
                    deleteFromFavoritePanel.removeAll();
                    deleteFromFavorite(deleteFromFavoritePanel);
                    deleteFromFavoritePanel.revalidate();
                    deleteFromFavoritePanel.repaint();
                    String message = "hello " + currentUser.getName() + "\n" +
                            "you have added " + production.getTitle() + " to your favorites";
                    dialogBox(message);
                }


            });
        }
        JLabel addToFavoriteLabel2 = ImdbLabel("Actors");
        addToFavoriteLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToFavoritePanel.add(addToFavoriteLabel2);

       for (Actor actor : actors) {
            JButton button = ImdbButton(actor.getName(),40);
            addToFavoritePanel.add(button);
            addToFavoritePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            button.addActionListener(e -> {
                if (currentUser.getFavoriteActors().contains(actor.getName())){
                    dialogBox("already in favorites");
                } else {
                    currentUser.addToFavoriteActors(actor);
                    //refresh the panel
                    deleteFromFavoritePanel.removeAll();
                    deleteFromFavorite(deleteFromFavoritePanel);
                    deleteFromFavoritePanel.revalidate();
                    deleteFromFavoritePanel.repaint();
                    String message = "hello " + currentUser.getName() + "\n" +
                            "you have added " + actor.getName() + " to your favorites";
                    dialogBox(message);
                }
            });
        }

    }

    private void deleteFromFavorite(JPanel deleteFromFavoritePanel) {
        // helper method for the addDeleteFavorites method to delete from favorites
        JLabel deleteFromFavoriteLabel = ImdbLabel("DELETE FROM FAVORITES");
        deleteFromFavoriteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteFromFavoritePanel.add(deleteFromFavoriteLabel);
        JLabel deleteFromFavoriteLabel2 = ImdbLabel("Favorite Productions");
        deleteFromFavoriteLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteFromFavoritePanel.add(deleteFromFavoriteLabel2);
        for (String production : currentUser.getFavoriteProductions()) {
            JButton button = ImdbButton(production,40);
            deleteFromFavoritePanel.add(button);
            deleteFromFavoritePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            button.addActionListener(e -> {
                currentUser.getFavoriteProductions().remove(production);
                deleteFromFavoritePanel.removeAll();
                deleteFromFavorite(deleteFromFavoritePanel);
                deleteFromFavoritePanel.revalidate();
                deleteFromFavoritePanel.repaint();
                String message = "hello " + currentUser.getName() + "\n" +
                        "you have removed " + production + " from your favorites";
                dialogBox(message);
            });
        }
        JLabel deleteFromFavoriteLabel3 = ImdbLabel("Favorite Actors");
        deleteFromFavoriteLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteFromFavoritePanel.add(deleteFromFavoriteLabel3);

        for (String actor : currentUser.getFavoriteActors()) {
            JButton button = ImdbButton(actor,40);
            deleteFromFavoritePanel.add(button);
            deleteFromFavoritePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            button.addActionListener(e -> {
                currentUser.getFavoriteActors().remove(actor);
                //refresh the panel
                deleteFromFavoritePanel.removeAll();
                deleteFromFavorite(deleteFromFavoritePanel);
                deleteFromFavoritePanel.revalidate();
                deleteFromFavoritePanel.repaint();
                String message = "hello " + currentUser.getName() + "\n" +
                        "you have removed " + actor + " from your favorites";
                dialogBox(message);
            });
        }
    }

    /**
     * createDeleteRequest method is used to create or delete a request.
     */
    private void createDeleteRequest() {
        JPanel createDeleteRequestPanel = new JPanel(new GridLayout(1, 2));
        JPanel deleteRequestPanel = new JPanel();
        deleteRequestPanel.setLayout(new BoxLayout(deleteRequestPanel, BoxLayout.Y_AXIS));

        JPanel addRequestPanel = new JPanel();
        addRequestPanel.setLayout(new BoxLayout(addRequestPanel,BoxLayout.Y_AXIS ));
         JLabel addRequestLabel = ImdbLabel("ADD REQUEST");
            addRequestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            addRequestPanel.add(addRequestLabel);
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setMargin(new Insets(10, 10, 10, 10));
        descriptionArea.setFont(new Font("Monospaced", Font.PLAIN, 24));
        descriptionArea.setPreferredSize(new Dimension(800, 600));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JComboBox<String> requestTypeComboBox = new JComboBox<>(new String[]{"DELETE_ACCOUNT", "ACTOR_ISSUE", "MOVIE_ISSUE", "OTHERS"});
        requestTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
         JTextField toField = ImdbTextField(20,40);
         JLabel descriptionLabel = ImdbLabel("Description: ");
         JLabel toLabel = ImdbLabel("Request To:  ");
         JLabel requestLabel = ImdbLabel("Request Type:              ");

         JPanel descriptionPanel =ImdbLabelScrollablePanel(descriptionLabel,new JScrollPane(descriptionArea));
         addRequestPanel.add(descriptionPanel);

         JPanel thirdFiled =  ImdbInputPanel(toLabel,toField);
        addRequestPanel.add(thirdFiled);

         JPanel secondFiled =  ImdbComboPanel(requestLabel,requestTypeComboBox);
         addRequestPanel.add(secondFiled);


        JPanel buttons = new JPanel();
        JButton createButton = ImdbButton("Create Request",40);
        createButton.addActionListener(e -> {
            RequestTypes requestTypes = null;
            int choice = requestTypeComboBox.getSelectedIndex();
            switch (choice){
                case 0->requestTypes=RequestTypes.DELETE_ACCOUNT;
                case 1->requestTypes=RequestTypes.ACTOR_ISSUE;
                case 2->requestTypes=RequestTypes.MOVIE_ISSUE;
                case 3->requestTypes=RequestTypes.OTHERS;
            }

            String description = descriptionArea.getText();
            String to = toField.getText();
            if (description.isEmpty()){
                dialogBox("one or more fields are empty");
            } else {
                Request request = new Request(requestTypes, description, currentUser.getUsername());
                if (!to.isEmpty()){
                    request.setTo(to);
                }
                IMDB.getInstance().getRequests().add(request);
                deleteRequestPanel.removeAll();
                deleteRequestMethod(deleteRequestPanel);
                deleteRequestPanel.revalidate();
                deleteRequestPanel.repaint();
                // and refresh the  add request panel
                // clear the fields
                descriptionArea.setText("");
                toField.setText("");
                requestTypeComboBox.setSelectedIndex(1);

            }

        });
        buttons.add(createButton);
        addRequestPanel.add(buttons);
        deleteRequestMethod(deleteRequestPanel);


        createDeleteRequestPanel.add(new JScrollPane(addRequestPanel));
        createDeleteRequestPanel.add(new JScrollPane(deleteRequestPanel));

         setCurrentPanel(createDeleteRequestPanel);


    }

    private void deleteRequestMethod(JPanel deleteRequestPanel) {
        // helper method for the createDeleteRequest method to delete a request
        JLabel deleteRequestLabel = ImdbLabel("DELETE REQUEST");
        deleteRequestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteRequestPanel.add(deleteRequestLabel);
        for (Request request : requests) {
            if (request.getUsername().equals(currentUser.getUsername())){
                JLabel RequestTypeLabel = ImdbLabel("Request Type: "+request.getType());
                JButton button = new JButton(GuiConstants.getIcon("delete.png",40,50));
                JPanel requestTypePanel = ImdbButtonLabelPanel(RequestTypeLabel,button);

                JButton detailsButton = new JButton(GuiConstants.getIcon("detail.png",40,50 ));
                detailsButton.addActionListener(e -> {
                    String message = "Request Type: " + request.getType() + "\n" +
                            "Description: " + request.getDescription() + "\n" +
                            "Request From: " + request.getUsername() + "\n" +
                            "Request To: " + request.getTo() + "\n" +
                            "Request Date: " + request.getFormattedCreationDate();
                    dialogArea(message);
                });
                requestTypePanel.add(detailsButton);
                deleteRequestPanel.add(requestTypePanel);
                deleteRequestPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                button.addActionListener(e -> {
                    request.displayInfo();
                    String message = "hello " + currentUser.getName() + "\n" +
                            "you have deleted " + request.getFormattedCreationDate() + " from your requests";
                    dialogBox(message);
                    IMDB.getInstance().getRequests().remove(request);
                    deleteRequestPanel.removeAll();
                    deleteRequestMethod(deleteRequestPanel);

                    deleteRequestPanel.revalidate();
                    deleteRequestPanel.repaint();
                });
            }
        }
    }

    /**
     * AddDeleteReview method is used to add or delete a review.
     */
    private void AddDeleteReview() {
        setTitle("Add/Delete a review for a product.");
        JPanel addDeleteReviewPanel = new JPanel(new GridLayout(1, 2));
        JPanel addReviewPanel = new JPanel(new FlowLayout());
        addReviewPanel.setLayout(new BoxLayout(addReviewPanel, BoxLayout.Y_AXIS));
        JLabel addReviewLabel = ImdbLabel("ADD REVIEW");
        addReviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addReviewPanel.add(addReviewLabel);
        JPanel deleteReviewPanel = new JPanel(new FlowLayout());

        deleteReviewPanel.setLayout(new BoxLayout(deleteReviewPanel, BoxLayout.Y_AXIS));
        JLabel deleteReviewLabel = ImdbLabel("DELETE REVIEW");
        deleteReviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteReviewPanel.add(deleteReviewLabel);
        deleteReview(deleteReviewPanel);
        addReview(addReviewPanel, deleteReviewPanel);

        addDeleteReviewPanel.add(new JScrollPane(addReviewPanel));
        addDeleteReviewPanel.add(new JScrollPane(deleteReviewPanel));


        setCurrentPanel(addDeleteReviewPanel);

    }
    private void addReview(JPanel addReviewPanel, JPanel deleteReviewPanel) {
        // helper method for the AddDeleteReview method to add a review to a production
        JLabel addReviewLabel = ImdbLabel("Productions");
        addReviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addReviewPanel.add(addReviewLabel);
        for (Production production : productions){
            JButton button = ImdbButton(production.getTitle(),40);
            addReviewPanel.add(button);
            addReviewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            button.addActionListener(e -> {
                String username = currentUser.getUsername();
                JTextArea commentArea = new JTextArea();
                commentArea.setMargin(new Insets(10, 10, 10, 10));
                commentArea.setFont(new Font("Monospaced", Font.PLAIN, 24));
                commentArea.setPreferredSize(new Dimension(800, 600));
                commentArea.setLineWrap(true);
                commentArea.setWrapStyleWord(true);

                JComboBox<String> ratingComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
                ratingComboBox.setFont(new Font("Arial", Font.BOLD, 40));
                JLabel descriptionLabel = ImdbLabel("Comment: ");
                JLabel ratingLabel = ImdbLabel("Score: ");

                JPanel commentPanelPanel =ImdbLabelScrollablePanel(descriptionLabel,new JScrollPane(commentArea));
                JPanel ratingPanel = ImdbComboPanel(ratingLabel,ratingComboBox);
                JPanel reviewPanel = new JPanel();
                reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
                reviewPanel.add(commentPanelPanel);
                reviewPanel.add(ratingPanel);
                int result = JOptionPane.showConfirmDialog(null, reviewPanel,
                        "Please Enter Review Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String comment = commentArea.getText();
                    int rating = ratingComboBox.getSelectedIndex() + 1;
                    if (comment.isEmpty()){
                        dialogBox("please enter some comment");
                    } else {
                        Rating rate = new Rating(username, rating,comment);
                       for (Rating checkRating : production.getRatings()){
                           if ( checkRating.getUsername().equals(username)){
                               String message = "hello " + currentUser.getName() + "\n" +
                                       "you have updated your review for " + production.getTitle()+" from\n"+
                                       "comment: "+checkRating.getComment()+"\n"+ "rating: "+checkRating.getRating()+"\n"+
                                       "to\n"+"comment: "+comment+"\n"+ "rating: "+rating;

                               checkRating.setRating(rating);
                                 checkRating.setComment(comment);
                                 dialogBox(message);
                               return;
                           }
                          }
                        production.addRating(rate);
                        dialogBox("added to system");
                        deleteReviewPanel.removeAll();
                        deleteReview(deleteReviewPanel);
                        deleteReviewPanel.revalidate();
                        deleteReviewPanel.repaint();
                    }

                }
            });
        }



    }


    private void deleteReview(JPanel deleteReviewPanel) {
        // helper method for the AddDeleteReview method to delete a review from a production
        JLabel deleteReviewLabel = ImdbLabel("Productions");
        deleteReviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteReviewPanel.add(deleteReviewLabel);
        for (Production production : productions){

            for (Rating rating : production.getRatings()){
                JPanel reviewPanel = new JPanel();
                if (rating.getUsername().equals(currentUser.getUsername())){
                    deleteReviewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    reviewPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
                    reviewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    JButton detailsButton = new JButton(GuiConstants.getIcon("detail.png",40,50 ));
                    JButton button = new JButton(GuiConstants.getIcon("delete.png",40,50));
                    JLabel productionLabel = ImdbLabel(production.getTitle());

                    reviewPanel.add(productionLabel);
                    reviewPanel.add(button);
                    reviewPanel.add(detailsButton);
                    deleteReviewPanel.add(reviewPanel);
                    reviewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    deleteReviewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    button.addActionListener(e -> {
                        production.getRatings().remove(rating);
                        deleteReviewPanel.removeAll();
                        deleteReview(deleteReviewPanel);
                        deleteReviewPanel.revalidate();
                        deleteReviewPanel.repaint();
                        String message = "hello " + currentUser.getName() + "\n" +
                                "you have deleted " + production.getTitle() + " from your reviews";
                        dialogBox(message);
                    });

                    detailsButton.addActionListener(e -> {
                        String message = "Username: " + rating.getUsername() + "\n" +
                                "Comment: " + rating.getComment() + "\n" +
                                "Rating: " + rating.getRating();
                        dialogArea(message);
                    });
                }
            }
        }

    }

    /**
     * addDeleteUser method is used to add or delete a user.
     */
    public void addDeleteUser() {
        setTitle("Add/Delete user");
        getDimension(screenSize);
        JPanel addUserPanel =  new JPanel(new FlowLayout());
        addUserPanel.setLayout(new BoxLayout(addUserPanel, BoxLayout.Y_AXIS));
        JLabel addUserLabel = ImdbLabel("ADD USER");
        addUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addUserPanel.add(addUserLabel);
        JPanel deleteUserPanel = new JPanel( new FlowLayout());
        JPanel addDeleteUserPanel = new JPanel(new GridLayout(1, 2));

       deleteUserPanel.setLayout(new BoxLayout(deleteUserPanel, BoxLayout.Y_AXIS));
       JLabel deleteUserLabel = ImdbLabel("DELETE USER");
       deleteUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
       deleteUserPanel.add(deleteUserLabel);
       deleteUser(deleteUserPanel, addDeleteUserPanel);
       addUser(addUserPanel, addDeleteUserPanel, deleteUserPanel);

       addDeleteUserPanel.add(new JScrollPane(deleteUserPanel));
       addDeleteUserPanel.add(new JScrollPane(addUserPanel));

        setCurrentPanel(addDeleteUserPanel);
    }

    private void addUser(JPanel addUserPanel, JPanel addDeleteUserPanel, JPanel deleteUserPanel) {
        // helper method for the addDeleteUser method to add a user
        JTextField FirstNameField = ImdbTextField(20,20);
        JTextField lastNameField = ImdbTextField(20,20);
        JTextField emailField = ImdbTextField(20,20);
        JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"Admin", "Contributor", "Regular"});
        userTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
        JButton button = ImdbButton("ADD USER",20);
        JLabel nameLabel = ImdbLabel("First Name: ");
        JLabel usernameLabel = ImdbLabel("Last Name: ");
        JLabel emailLabel = ImdbLabel("Email: ");
        JLabel userTypeLabel = ImdbLabel("User Type: ");
        JPanel namePanel = ImdbInputPanel(nameLabel,FirstNameField);
        JPanel usernamePanel = ImdbInputPanel(usernameLabel,lastNameField);
        JPanel emailPanel = ImdbInputPanel(emailLabel,emailField);
        JPanel userTypePanel = ImdbComboPanel(userTypeLabel,userTypeComboBox);


        //age
        JLabel userAgeLabel = ImdbLabel("Age: ");
        //  JTextField userAgeField = ImdbTextField(20,20);
        JComboBox<Integer> userAgeComboBox = new JComboBox<>(GuiConstants.getAgeList());
        JPanel userAgePanel = ImdbComboPanel(userAgeLabel,userAgeComboBox);
        //gender
        JLabel userGenderLabel = ImdbLabel("Gender: ");
        JComboBox<String> userGender = new JComboBox<>(new String[]{"male", "female", "neutral"});
        userGender.setFont(new Font("Arial", Font.BOLD, 40));
        JPanel userGenderPanel = ImdbComboPanel(userGenderLabel,userGender);
        //country
        JLabel userCountryLabel = ImdbLabel("Country: ");
        JTextField userCountryField = ImdbTextField(20,20);
        JPanel userCountryPanel = ImdbInputPanel(userCountryLabel,userCountryField);
        // birthday
        JPanel userBirthdayPanel = new JPanel();
        JLabel userBirthdayLabel = ImdbLabel("Birthday: ");
        userBirthdayPanel.add(userBirthdayLabel);
        JPanel birthDayPanel = new JPanel(new FlowLayout());
        JLabel yearLabel = ImdbLabel("Year: ");
        JLabel monthLabel = ImdbLabel("Month: ");
        JLabel dayLabel = ImdbLabel("Day: ");
        JComboBox<Integer> yearComboBox = GuiConstants.createComboBox(GuiConstants.getYearList());
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"});
        JComboBox<Integer> dayComboBox = GuiConstants.createComboBox(GuiConstants.getDayList());
        birthDayPanel.add(yearLabel);
        birthDayPanel.add(yearComboBox);
        birthDayPanel.add(monthLabel);
        birthDayPanel.add(monthComboBox);
        birthDayPanel.add(dayLabel);
        birthDayPanel.add(dayComboBox);
        userBirthdayPanel.add(birthDayPanel);

        button.addActionListener(e -> {
            String firstname = FirstNameField.getText();
            String lastname = lastNameField.getText();
            String name = firstname + " " + lastname;
            String email = emailField.getText();
            int age = userAgeComboBox.getSelectedIndex()+1;
            //birthday

                int year = (int) yearComboBox.getSelectedItem();
                int month = monthComboBox.getSelectedIndex() + 1;
                int day = (int) dayComboBox.getSelectedItem();
            LocalDateTime    birthday = LocalDateTime.of(year, month, day, 0, 0);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int i = currentYear - year;
            int choice = userTypeComboBox.getSelectedIndex();
            AccountType accountType = null;
            String username;
            switch (choice){
                case 0->accountType=AccountType.Admin;
                case 1->accountType=AccountType.Contributor;
                case 2->accountType=AccountType.Regular;
            }
            if(lastname.isEmpty() || firstname.isEmpty() || email.isEmpty()){
                dialogBox("one or more fields are empty");
                // check if the email is valid
            }else if( !email.matches("^(.+)@(.+)$")){
                dialogBox("invalid email address");
            }else if(i+1 < age || i-1 > age){
                dialogBox("your age may not be correct ");
            }else{
                username = FunctionsFactory.generateUniqueUsername(name);
                Admin<?> admin = new Admin<>();
                User<?> newUser = admin.addUser(name, accountType);
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setUsername(username);
                IMDB.getInstance().getUsers().add(newUser);
                String password = FunctionsFactory.generateRandomPassword();
                newUser.setPassword(password);
                String message = "Hello " + name + ",\n\n" +
                        "Welcome to IMDB. Your account has been created successfully.\n" +
                        "Your username is: " + username + "\n\n" +
                        "Your password is: " + password + "\n\n" +
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message );
                newUser.setAge(age);
                if(!userCountryField.getText().isEmpty()){
                    newUser.setCountry(userCountryField.getText());
                }
                if(!Objects.requireNonNull(userGender.getSelectedItem()).toString().isEmpty()){
                    newUser.setGender(userGender.getSelectedItem().toString());
                }
                    newUser.setBirthDate(birthday);

                // renew the user list
                addUserPanel.removeAll();
                addUser(addUserPanel, addDeleteUserPanel, deleteUserPanel);
                deleteUserPanel.removeAll();
                deleteUser(deleteUserPanel, addDeleteUserPanel);
                addUserPanel.revalidate();
                addUserPanel.repaint();
                deleteUserPanel.revalidate();
                deleteUserPanel.repaint();

            }
            //add extra info for the user


        });
        addUserPanel.add(namePanel);
        addUserPanel.add(usernamePanel);
        addUserPanel.add(emailPanel);
        addUserPanel.add(userTypePanel);
        //add extra info for the user
        addUserPanel.add(userAgePanel);
        addUserPanel.add(userGenderPanel);
        addUserPanel.add(userCountryPanel);
        addUserPanel.add(userBirthdayPanel);


        //
        addUserPanel.add(button);
        addUserPanel.add(Box.createRigidArea(new Dimension(0, 20)));


    }

    private void deleteUser(JPanel deleteUserPanel, JPanel addDeleteUserPanel) {
        // helper method for the addDeleteUser method to delete a user
        for (User<?> user : users) {
            JButton nameButton = ImdbButton(user.getName(),40);
            JButton button = new JButton(GuiConstants.getIcon("delete.png",40,50));
            JPanel namePanel = new JPanel();
            namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
            namePanel.add(nameButton);
            namePanel.add(button);
            deleteUserPanel.add(namePanel);
            deleteUserPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            nameButton.addActionListener(e -> {
                // display user info
                List<String> info = Constants.userInfo(user);
                StringBuilder message = new StringBuilder();
                for (String s : info) {
                    message.append(s).append("\n");
                }
               imdbTextAreaWithBack(addDeleteUserPanel, user.getUsername(), message.toString());
            });
            button.addActionListener(e -> {
                users.remove(user);
                //delete rating by the user
                for (Production production : productions) {
                    production.getRatings().removeIf(rating -> rating.getUsername().equals(user.getUsername()));
                }
                //delete requests by the user
                requests.removeIf(request -> request.getUsername().equals(user.getUsername()));
                // renew the user list
                 deleteUserPanel.removeAll();
                deleteUser(deleteUserPanel, addDeleteUserPanel);
                deleteUserPanel.revalidate();
                deleteUserPanel.repaint();

                String message = "Hello Admin " + currentUser.getName() + ",\n\n" +
                        "The user " + user.getName() + " has been deleted from the system.\n\n" +
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message);
            });
          }
    }

    /**
     * addDeleteProductionSystem method is used to add or delete a production or an actor from the system.
     */
    public void addDeleteProductionOrActorFromSystem() {
        setTitle("Add/Delete actor/movie/series/ from system");
        getDimension(screenSize);
        JPanel deleteProductionOrActorPanel = new JPanel(new FlowLayout());
        JPanel addDeleteProductionPanel = new JPanel(new GridLayout(1, 2));
        JPanel addProductionPanel =  new JPanel(new FlowLayout());
     addProductionPanel.setLayout(new BoxLayout(addProductionPanel, BoxLayout.Y_AXIS));
    deleteProductionOrActorPanel.setLayout(new BoxLayout(deleteProductionOrActorPanel, BoxLayout.Y_AXIS));
    JLabel addProductionLabel = ImdbLabel("ADD PRODUCTION");
    addProductionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    addProductionPanel.add(addProductionLabel);

    JLabel deleteProductionLabel = ImdbLabel("DELETE PRODUCTION");
    deleteProductionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    deleteProductionOrActorPanel.add(deleteProductionLabel);
        deleteProductionOrActor(deleteProductionOrActorPanel);
        addProductionOrActor(addProductionPanel);

    addDeleteProductionPanel.add(new JScrollPane(deleteProductionOrActorPanel));
    addDeleteProductionPanel.add(new JScrollPane(addProductionPanel));

        setCurrentPanel(addDeleteProductionPanel);
    }
private  void addProductionOrActor(JPanel addProductionPanel){
        // helper method for the addDeleteProductionOrActorFromSystem method to add a production or an actor
    addaActorOrProductionComboBox = new JComboBox<>(new String[]{"Actor", "Production"});
    addaActorOrProductionComboBox.setFont(new Font("Arial", Font.BOLD, 20));
    addProductionPanel.add(addaActorOrProductionComboBox);
    addaActorOrProductionComboBox.addActionListener(e -> {
        addProductionPanel.add(addaActorOrProductionComboBox);
        if (addaActorOrProductionComboBox.getSelectedIndex() == 0) {
            addProductionPanel.removeAll();
            addProductionOrActor(addProductionPanel);
            addActor(addProductionPanel);
            addProductionPanel.revalidate();
            addProductionPanel.repaint();
        } else if (addaActorOrProductionComboBox.getSelectedIndex() == 1){
            addProductionPanel.removeAll();
            addProductionOrActor(addProductionPanel);
            addProduction(addProductionPanel);
            addProductionPanel.revalidate();
            addProductionPanel.repaint();
        }
    });
}

    private void addProduction(JPanel addProductionPanel) {
        // helper method for addProductionOrActor method to add a production
        JLabel titleLabel = ImdbLabel("Title: ");
       JTextField titleField = ImdbTextField(20,20);

        JLabel typeLabel = ImdbLabel("Type: ");
       JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Movie", "Series"});
         typeComboBox.setFont(new Font("Arial", Font.BOLD, 20));
         JLabel plotLabel = ImdbLabel("Plot: ");
        JTextArea plotArea= new JTextArea();
        plotArea.setFont(new Font("Arial", Font.BOLD, 40));
        plotArea.setPreferredSize(new Dimension(600, 400));
        JScrollPane plotScrollPane = new JScrollPane(plotArea);
        JLabel releaseYearLabel = ImdbLabel("Release Year: ");
       JComboBox<Integer> releaseYearField = GuiConstants.createComboBox(GuiConstants.getYearList());

        JPanel titlePanel = ImdbInputPanel(titleLabel,titleField);
        JPanel typePanel = ImdbComboPanel(typeLabel,typeComboBox);
        JPanel plotPanel = ImdbLabelScrollablePanel(plotLabel,plotScrollPane);
        JPanel releaseYearPanel = ImdbComboPanel(releaseYearLabel,releaseYearField);
        addProductionPanel.add(titlePanel);
        addProductionPanel.add(typePanel);
        addProductionPanel.add(plotPanel);
        addProductionPanel.add(releaseYearPanel);

        JButton createButton = ImdbButton("Create Production",40);
        createButton.addActionListener(e -> {
            String title = titleField.getText();
            String plot = plotArea.getText();
            if(title.isEmpty()){
                dialogBox("title is empty");
            }else {
                int choice = typeComboBox.getSelectedIndex();
                Production production = null;
                switch (choice) {
                    case 0 -> production = new Movie();
                    case 1 -> production = new Series();
                }
                assert production != null;
                production.setTitle(title);
                if(production instanceof Movie){
                    ((Movie) production).setReleaseYear(Integer.parseInt(Objects.requireNonNull(releaseYearField.getSelectedItem()).toString()));
                }else {
                    ((Series) production).setReleaseYear(Integer.parseInt(Objects.requireNonNull(releaseYearField.getSelectedItem()).toString()));
                }
                production.setType(Objects.requireNonNull(typeComboBox.getSelectedItem()).toString());
                production.setPlot(plot);
                productions.add(production);
                String message = "Hello Admin " + currentUser.getName() + ",\n\n" +
                        "The production " + production.getTitle() + " has been added to the system.\n\n" +
                        "you can update it if you want to add more details"+
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message);
                // renew the user list
                addProductionPanel.removeAll();
                addProductionPanel.add(addaActorOrProductionComboBox);
                addProduction(addProductionPanel);
                addProductionPanel.revalidate();
                addProductionPanel.repaint();
            }});
        addProductionPanel.add(createButton);


    }


    private void addActor(JPanel addProductionPanel) {
        // helper method for addProductionOrActor method to add an actor
        JTextField nameField = ImdbTextField(20,20);
        JLabel nameLabel = ImdbLabel("Name: ");
        JPanel namePanel = ImdbInputPanel(nameLabel,nameField);
        addProductionPanel.add(namePanel);
        JLabel biographyLabel = ImdbLabel("Biography: ");
        JTextArea biographyArea= new JTextArea();
        biographyArea.setFont(new Font("Arial", Font.BOLD, 40));
        biographyArea.setPreferredSize(new Dimension(600, 400));

        JScrollPane biographyScrollPane = new JScrollPane(biographyArea);
        JPanel biographyPanel = ImdbLabelScrollablePanel(biographyLabel,biographyScrollPane);
        JLabel performanceLabel = ImdbLabel("Number of Performances: ");
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setFont(new Font("Arial", Font.BOLD, 40));
        JPanel spinnerPanel = new JPanel(new FlowLayout());
        JPanel performancePanel = new JPanel();
         performancePanel.setLayout(new BoxLayout(performancePanel, BoxLayout.Y_AXIS));
        spinnerPanel.add(performanceLabel);
        spinnerPanel.add(spinner);
        performancePanel.add(spinnerPanel);
        addProductionPanel.add(performancePanel);
        List<Map.Entry<String, String>> performances = new ArrayList<>();
        spinner.addChangeListener(e -> {
            JPanel performanceBoxPanel = new JPanel();
            performanceBoxPanel.setLayout(new BoxLayout(performanceBoxPanel, BoxLayout.Y_AXIS));
            int numberOfPerformances = (int) spinner.getValue();
            for (int i = 0; i < numberOfPerformances; i++) {
                JPanel flowPanel = new JPanel(new FlowLayout());
                JTextField titleField = ImdbTextField(20,20);
                JLabel titleLabel = ImdbLabel(" Title: ");
                JPanel titlePanel = ImdbInputPanelFlow(titleLabel,titleField);
                flowPanel.add(titlePanel);
                JLabel typeLabel = ImdbLabel(" Type: ");
                JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Movie", "Series"});
                typeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
                JPanel typePanel = ImdbComboPanel(typeLabel,typeComboBox);
                flowPanel.add(typePanel);
                performanceBoxPanel.add(flowPanel);
                performanceBoxPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                performances.add(new AbstractMap.SimpleEntry<>(titleField.getText(), Objects.requireNonNull(typeComboBox.getSelectedItem()).toString()));
            }
            performancePanel.add(performanceBoxPanel);
            performancePanel.removeAll();
            spinnerPanel.add(performanceLabel);
            spinnerPanel.add(spinner);
            performancePanel.add(spinnerPanel);
            performancePanel.add(performanceBoxPanel);
            performancePanel.revalidate();
            performancePanel.repaint();


        });

        JButton button = ImdbButton("ADD ACTOR",20);
        button.addActionListener(e -> {
            String name = nameField.getText();
            String biography = biographyArea.getText();
            if(name.isEmpty()){
                dialogBox(" name can't be empty");
            }else{
                Actor actor = new Actor(name,performances,biography);
                actor.setPerformances(performances);
                actors.add(actor);
                String message = "Hello Admin " + currentUser.getName() + ",\n\n" +
                        "The actor " + actor.getName() + " has been added to the system.\n\n" +
                        "you can update it if you want to add more details"+
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message);
                // renew the user list
                addProductionPanel.removeAll();
                addProductionPanel.add(addaActorOrProductionComboBox);
                addActor(addProductionPanel);

                addProductionPanel.revalidate();
                addProductionPanel.repaint();


            }

        });

        addProductionPanel.add(performancePanel);
        addProductionPanel.add(biographyPanel);
        addProductionPanel.add(button);
    }

    private void deleteProductionOrActor( JPanel deleteProduction){
        // helper method for the addDeleteProductionOrActorFromSystem method to delete a production or an actor
        deleteActorOrProductionComboBox = new JComboBox<>(new String[]{"Actor", "Production"});
         deleteActorOrProductionComboBox.setPreferredSize(new Dimension(200, 50));
        deleteActorOrProductionComboBox.setFont(new Font("Arial", Font.BOLD, 40));
        deleteProduction.add(deleteActorOrProductionComboBox);
        deleteActorOrProductionComboBox.addActionListener(e -> {
            deleteProduction.add(deleteActorOrProductionComboBox);
            if (deleteActorOrProductionComboBox.getSelectedIndex() == 0) {
                deleteProduction.removeAll();
                deleteProductionOrActor(deleteProduction);
                deleteActor(deleteProduction);
                deleteProduction.revalidate();
                deleteProduction.repaint();
            } else if (deleteActorOrProductionComboBox.getSelectedIndex() == 1){
                deleteProduction.removeAll();
                deleteProductionOrActor(deleteProduction);
                deleteProduction(deleteProduction);
                deleteProduction.revalidate();
                deleteProduction.repaint();
            }
        });


    }

    private void deleteProduction(JPanel deleteProduction) {
        // helper method for deleteProductionOrActor method to delete a production
        for (Production production : productions) {
            JLabel nameLabel = ImdbLabel(production.getTitle()+"  ");
            JButton button = new JButton(GuiConstants.getIcon("delete.png",40,50));
            deleteProduction.add(ImdbButtonLabelPanel(nameLabel, button));
            deleteProduction.add(Box.createRigidArea(new Dimension(0, 20)));

            button.addActionListener(e -> {
                productions.remove(production);
                //delete rating by the user
                for (User<?> user : users) {
                    user.getFavoriteProductions().removeIf(favoriteProduction -> favoriteProduction.equals(production.getTitle()));
                }
                // renew the user list
                deleteProduction.removeAll();
                deleteProduction.add(deleteActorOrProductionComboBox);
                deleteProduction(deleteProduction);
                deleteProduction.revalidate();
                deleteProduction.repaint();

                String message = "Hello Admin " + currentUser.getName() + ",\n\n" +
                        "The production " + production.getTitle() + " has been deleted from the system.\n\n" +
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message);
            });
        }
    }

    private void deleteActor(JPanel deleteProduction) {
        // helper method for deleteProductionOrActor method to delete an actor
        for (Actor actor : actors) {
            JLabel nameLabel = ImdbLabel(actor.getName()+"  ");
            JButton button = new JButton(GuiConstants.getIcon("delete.png",40,50));
            deleteProduction.add(ImdbButtonLabelPanel(nameLabel, button));
            deleteProduction.add(Box.createRigidArea(new Dimension(0, 20)));

            button.addActionListener(e -> {
                actors.remove(actor);
                //delete rating by the user
                for (User<?> user : users) {
                    user.getFavoriteActors().removeIf(favoriteActor -> favoriteActor.equals(actor.getName()));
                }
                // renew the user list
                deleteProduction.removeAll();
                deleteProduction.add(deleteActorOrProductionComboBox);
                deleteActor(deleteProduction);
                deleteProduction.revalidate();
                deleteProduction.repaint();

                String message = "Hello Admin " + currentUser.getName() + ",\n\n" +
                        "The actor " + actor.getName() + " has been deleted from the system.\n\n" +
                        "Best regards,\n" +
                        "IMDB Team";
                dialogBox(message);
            });
        }
    }

    /**
     * updateMovieDetails method is used to update the details of a movie or a series.
     */


    public void updateMovieDetails() {
        //getDimension(screenSize);
        JPanel updateMoviePanel = new JPanel(new GridLayout(1, 2));
     JPanel listPanel = new JPanel(new BorderLayout());
     JPanel detailsPanel = new JPanel(new BorderLayout());
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setSize(screenSize.width*3/4, screenSize.height);
        listPanel.setSize(screenSize.width/4, screenSize.height);
        productions.sort(Comparator.comparing(Production::getTitle));
        for (Production production : productions) {
            JButton button = ImdbButton(production.getTitle(), 40);
            listPanel.add(button);
            listPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            button.addActionListener(e -> {
                String type = production.getType();
                JPanel editPanel = null;
                if(type.equals("Movie")){
                    editPanel = editMovieDetails(production);
                }else if(type.equals("Series")){
                    editPanel = editSeriesDetails(production);

                }
                assert editPanel != null;
                editPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                detailsPanel.removeAll();
                detailsPanel.add(editPanel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
            });
        }
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        JScrollPane scrollPane1 = new JScrollPane(detailsPanel);
        updateMoviePanel.add(scrollPane);
        updateMoviePanel.add(scrollPane1);
        setCurrentPanel(updateMoviePanel);


    }

   private JPanel editMovieDetails(Production production){
        // helper method for the updateMovieDetails method to update the details of a movie
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        // title
       editTitle(production, panel);
       //type
       editProductionType(production, panel);
       //directors
       editProductionDirectors(production, panel);
       //actors
       editProductionActors(production, panel);

       //genres
       editProductionGenres(production, panel);

       // ratings
       editProductionRatings(production, panel);

       //plot
       editProductionPlot(production, panel);

       //duration
        JButton durationButton = ImdbButton(((Movie)production).getDuration(),40);
        durationButton.addActionListener(e->{
            String duration = GuiConstants.showInputDialog("Enter new duration", ((Movie)production).getDuration(), 300, 200);
            if(duration == null) return; // User canceled the input
            ((Movie)production).setDuration(duration);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JLabel durationLabel = ImdbLabel("Duration:");
        JPanel durationPanel = ImdbButtonLabelPanel(durationLabel,durationButton);
        // make the entries start at the beginning of the line
        durationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(durationPanel);

        //release year
       editProductionReleaseYear(production, panel);


       return panel;
   }



    private JPanel editSeriesDetails(Production production){
        // helper method for the updateMovieDetails method to update the details of a series
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
       // title
        editTitle(production, panel);
        //type
        editProductionType(production, panel);
        //directors
        editProductionDirectors(production, panel);
        //actors
        editProductionActors(production, panel);
        // genres
        editProductionGenres(production, panel);
        // ratings
        editProductionRatings(production, panel);
        //plot
        editProductionPlot(production, panel);
        // release year
        editProductionReleaseYear(production, panel);
        //number of seasons
        JButton seasonsButton = ImdbButton(String.valueOf(((Series)production).getNumSeasons()),40);
        seasonsButton.addActionListener(e->{
            String seasons = GuiConstants.showInputDialog("Enter new number of seasons", String.valueOf(((Series)production).getNumSeasons()), 300, 200);
            if(seasons == null) return; // User canceled the input
            ((Series)production).setNumSeasons(Integer.parseInt(seasons));
            panel.removeAll();
            panel.add(editSeriesDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JLabel seasonsLabel = ImdbLabel("Number of seasons:");
        JPanel seasonsPanel = ImdbButtonLabelPanel(seasonsLabel,seasonsButton);
        // make the entries start at the beginning of the line
        seasonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(seasonsPanel);

        // season details
        JPanel seasonDetailsPanel = new JPanel(new BorderLayout());
        seasonDetailsPanel.setLayout(new BoxLayout(seasonDetailsPanel, BoxLayout.Y_AXIS));
        // make the entries start at the beginning of the line
        seasonDetailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        Map<String, List<Episode>> seasons = ((Series)production).getSeasons();
        for(Map.Entry<String , List<Episode>> entry: seasons.entrySet()){
            JPanel seasonPanel = new JPanel(new BorderLayout());
            seasonPanel.setLayout(new BoxLayout(seasonPanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            seasonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton seasonButton = ImdbButton(entry.getKey(),40);
            seasonButton.addActionListener(e->{
                String season = GuiConstants.showInputDialog("Enter new season", entry.getKey(), 300, 200);
                if(season == null) return; // User canceled the input
                ((Series)production).setKey(entry.getKey(),season);
                panel.removeAll();
                panel.add(editSeriesDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            seasonPanel.add(seasonButton);

            List<Episode> episodes = entry.getValue();
            for(Episode episode:episodes){
                JPanel episodePanel = new JPanel(new BorderLayout());
                episodePanel.setLayout(new BoxLayout(episodePanel, BoxLayout.Y_AXIS));
                // make the entries start at the beginning of the line
                episodePanel.add(Box.createRigidArea(new Dimension(0, 20)));
                JButton episodeButton = ImdbButton(episode.getEpisodeName(),40);
                episodeButton.addActionListener(e->{
                    String entryKey = entry.getKey();
                     String duration = episode.getDuration();

                    String episodeTitle = GuiConstants.showInputDialog("Enter new episode title", episode.getEpisodeName(), 300, 200);
                    if(episodeTitle == null) return; // User canceled the input
                    Episode newEpisode = new Episode(episodeTitle,duration);
                     episodes.remove(episode);
                        episodes.add(newEpisode);
                    ((Series)production).setValue(entryKey, episodes);
                    panel.removeAll();
                    panel.add(editSeriesDetails(production));
                    panel.revalidate();
                    panel.repaint();
                });
                JButton episodeDurationButton = ImdbButton("\t"+episode.getDuration(),40);
                episodeDurationButton.addActionListener(e->{
                    String entryKey = entry.getKey();
                    String episodeTitle = episode.getEpisodeName();
                    String episodeDuration = GuiConstants.showInputDialog("Enter new episode duration", episode.getDuration(), 300, 200);
                    if(episodeDuration == null) return; // User canceled the input
                    Episode newEpisode = new Episode(episodeTitle,episodeDuration);
                    episodes.remove(episode);
                    episodes.add(newEpisode);
                    ((Series)production).setValue(entryKey, episodes);
                    panel.removeAll();
                    panel.add(editSeriesDetails(production));
                    panel.revalidate();
                    panel.repaint();
                });
                episodePanel.add(episodeButton);
                episodePanel.add(episodeDurationButton);
                seasonPanel.add(episodePanel);
            }
            seasonDetailsPanel.add(seasonPanel);


        }
        panel.add(seasonDetailsPanel);

        return panel;
  }

    /**
     * updateActorDetails method is used to update the details of an actor.
     */
    public void updateActorDetails() {

        getDimension(screenSize);
        JPanel updateActorPanel = new JPanel(new GridLayout(1, 2));
        JPanel listPanel = new JPanel(new BorderLayout());
        JPanel detailsPanel = new JPanel(new BorderLayout());
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setSize(screenSize.width*3/4, screenSize.height);
        listPanel.setSize(screenSize.width/4, screenSize.height);
        actors.sort(Comparator.comparing(Actor::getName));
        for (Actor actor : actors) {
            JButton button = ImdbButton(actor.getName(), 40);
            listPanel.add(button);
            listPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            button.addActionListener(e -> {
               JPanel editPanel = editActorDetails(actor);
                editPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                detailsPanel.removeAll();
                detailsPanel.add(editPanel);
                detailsPanel.revalidate();
                detailsPanel.repaint();
            });
        }
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        JScrollPane scrollPane1 = new JScrollPane(detailsPanel);
        updateActorPanel.add(scrollPane);
        updateActorPanel.add(scrollPane1);
        setCurrentPanel(updateActorPanel);

    }
    private JPanel editActorDetails(Actor actor ){
        // helper method for the updateActorDetails method to update the details of an actor

        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton nameButton = ImdbButton(actor.getName(),40);
        nameButton.addActionListener(e->{
            String name = GuiConstants.showInputDialog("Enter new name", actor.getName(), 300, 200);
            if(name == null) return; // User canceled the input
            actor.setName(name);
            panel.removeAll();
            panel.add(editActorDetails(actor));
            panel.revalidate();
            panel.repaint();

        });
        JPanel namePanel = ImdbButtonLabelPanel(ImdbLabel("Name:"),nameButton);
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
        panel.add(namePanel);
        JPanel addPerformancePanel = new JPanel();
        addPerformancePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
        JLabel nameLabel = ImdbLabel("Performance:");
        JButton addPerformanceButton = new JButton(GuiConstants.getIcon("add.png", 40,50));
addPerformanceButton.addActionListener(e->{
            String title = GuiConstants.showInputDialog("Enter new title", "", 300, 200);
            if(title == null) return; // User canceled the input
            String type = GuiConstants.chooseType("Choose type" ,"Movie", "Series");
            if(type == null) return; // User canceled the input
           for (Map.Entry<String, String> performance : actor.getPerformances()) {
               if(performance.getKey().equals(title) && performance.getValue().equals(type)){
                   String message = "this performance is already added";
                   dialogBox(message);
                   return;
               }
              }
            actor.addPerformance(title,type);
            panel.removeAll();
            panel.add(editActorDetails(actor));
            panel.revalidate();
            panel.repaint();
        });
        addPerformancePanel.add(nameLabel);
        addPerformancePanel.add(addPerformanceButton);
        panel.add(addPerformancePanel);
        int size = actor.getNumberOfPerformances();
        for(int i=0;i<size;i++){
            JPanel performancePanel = new JPanel(new BorderLayout());
            performancePanel.setLayout(new BoxLayout(performancePanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            performancePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton  titleButton = ImdbButton(actor.getPerformances().get(i).getKey(),40);
            int finalI = i;
            titleButton.addActionListener(e->{
                String title = GuiConstants.showInputDialog("Enter new title", actor.getPerformances().get(finalI).getKey(), 300, 200);
                if(title == null) return; // User canceled the input
                actor.setKey(actor.getPerformances().get(finalI).getKey(),title);
                panel.removeAll();
                panel.add(editActorDetails(actor));
                panel.revalidate();
                panel.repaint();
            });
             JButton typeButton = ImdbButton(actor.getPerformances().get(i).getValue(),40);
            typeButton.addActionListener(e->{
                String type =  GuiConstants.chooseType("Choose type" ,"Movie", "Series");
                if(type == null) return; // User canceled the input
                actor.setValue(actor.getPerformances().get(finalI).getKey(),type);
                panel.removeAll();
                panel.add(editActorDetails(actor));
                panel.revalidate();
                panel.repaint();
            });
            JPanel titlePanel = ImdbButtonLabelPanel(ImdbLabel("Title:"),titleButton);
            JPanel typePanel = ImdbButtonLabelPanel(ImdbLabel("Type:"),typeButton);
            titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
            typePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
            performancePanel.add(titlePanel);
            performancePanel.add(typePanel);
            panel.add(performancePanel);


        }


        JButton biographyButton = new JButton(GuiConstants.getIcon("edit.png", 40,50));
        biographyButton.addActionListener(e->{
            String biography = GuiConstants.showInputDialog("Enter new biography", actor.getBiography(), 600, 400);
            if(biography == null) return; // User canceled the input
            actor.setBiography(biography);
            panel.removeAll();
            panel.add(editActorDetails(actor));
            panel.revalidate();
            panel.repaint();
        });
        JLabel biographyLabel = ImdbLabel("Biography:");
        JPanel biographyPanel = ImdbButtonLabelPanel(biographyLabel,biographyButton);
        biographyPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));

        panel.add(biographyPanel);
        JTextArea biographyArea = new JTextArea(GuiConstants.breakString(actor.getBiography(), 50));
        biographyArea.setEditable(false);
        biographyArea.setFont(new Font("Arial", Font.BOLD, 40));

        panel.add(biographyArea);

        return panel;
    }
    /**
     * solveRequests method is used to solve the requests of the users.
     */

    public void solveRequests() {
        getDimension(screenSize);
        JPanel solveRequests =  new JPanel();



    }
    /**
     * logout method is used to logout from the system and go back to the login screen.
     */
   public void logout() {
            getDimension(screenSize);
            setVisible(false);
           new ApplicationFlowGUI();

        }
        /**
         * exit method is used to exit from the system.
         */
    public void exit(){
        System.exit(0);
    }

   private void home(){
        // go back to the home screen
         setCurrentPanel(mainScreenPanel);
   }
    private void getDimension(Dimension screenSize) {
        // get the dimension of the screen
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

    //PRODUCTION EDIT FUNCTIONS
    private void editProductionReleaseYear(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the release year of a production

        JButton releaseDateButton = ImdbButton("",40);
          if (production instanceof Series){
              releaseDateButton = ImdbButton(String.valueOf(((Series) production).getReleaseYear()),40);
          } else if (production instanceof Movie){
              releaseDateButton = ImdbButton(String.valueOf(((Movie) production).getReleaseYear()),40);
          }
        releaseDateButton.addActionListener(e->{
            int releaseYear = GuiConstants.selectRealeaseYear("choose release year");


            if (production instanceof Series){
                ((Series) production).setReleaseYear(releaseYear);
            } else if (production instanceof Movie){
                ((Movie) production).setReleaseYear(releaseYear);
            }
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JLabel releaseDateLabel = ImdbLabel("Release Date:");
        JPanel releaseDatePanel = ImdbButtonLabelPanel(releaseDateLabel,releaseDateButton);
        // make the entries start at the beginning of the line
        releaseDatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(releaseDatePanel);
    }

    private void editProductionPlot(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the plot of a production
        JButton plotButton = new JButton(GuiConstants.getIcon("edit.png", 60,60));
        plotButton.addActionListener(e->{
            String plot = GuiConstants.showInputDialog("Enter new plot", production.getPlot(), 600, 400);
            if(plot == null) return; // User canceled the input
            production.setPlot(plot);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JLabel plotLabel = ImdbLabel("Plot:");
        JPanel plotPanel = ImdbButtonLabelPanel(plotLabel,plotButton);
        // make the entries start at the beginning of the line
        plotPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(plotPanel);
        JTextArea plotArea = new JTextArea(GuiConstants.breakString(production.getPlot(), 50));
        plotArea.setEditable(false);
        plotArea.setFont(new Font("Arial", Font.BOLD, 40));

        panel.add(plotArea);
    }

    private void editProductionRatings(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the ratings of a production
        int size;
        JLabel ratingsLabel = ImdbLabel("Ratings:");
        JButton addRatingButton = new JButton(GuiConstants.getIcon("add.png", 60,60));
        addRatingButton.addActionListener(e->{
            String username = currentUser.getUsername();
            int rating = GuiConstants.selectRating("choose rating you want to add");
            if(rating < 1) return; // User canceled the input
            String comment = GuiConstants.showInputDialog("Enter new comment", "", 300, 200);
            if(comment == null) return; // User canceled the input
            for (Rating rating1 : production.getRatings()) {
                if (rating1.getUsername().equals(username)) {
                    dialogBox("you already rated this production");
                    return;
                }
            }
            production.addRating(new Rating(username,rating,comment));
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JPanel addRatingPanel = ImdbButtonLabelPanel(ratingsLabel,addRatingButton);

        JPanel ratingsPanel = new JPanel(new BorderLayout());
        ratingsPanel.setLayout(new BoxLayout(ratingsPanel, BoxLayout.Y_AXIS));
        ratingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ratingsPanel.add(addRatingPanel);
        size = production.getRatings().size();
        for(int i=0;i<size;i++){
            JPanel mainRatingPanel = new JPanel(new BorderLayout());
            mainRatingPanel.setLayout(new BoxLayout(mainRatingPanel, BoxLayout.Y_AXIS));
            mainRatingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JPanel ratingPanel = new JPanel(new BorderLayout());
            ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            ratingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JLabel usernameLabel = ImdbLabel("Username: ");
            JButton  usernameButton = ImdbButton(production.getRatings().get(i).getUsername(),40);
            int finalI = i;
            usernameButton.addActionListener(e->{
                String newName = GuiConstants.showInputDialog("Enter new rating", production.getRatings().get(finalI).getUsername(), 300, 200);
                if(newName == null) return; // User canceled the input
                production.getRatings().get(finalI).setUsername(newName);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });

            ratingPanel.add(ImdbButtonLabelPanel(usernameLabel,usernameButton));
            ratingsPanel.add(ratingPanel);
            JButton  ratingButton = ImdbButton(String.valueOf(production.getRatings().get(i).getRating()),40);
            ratingButton.addActionListener(e->{
               int newRating = GuiConstants.selectRating("choose rating you want to add");
                if(newRating < 1) return; // User canceled the input
                production.getRatings().get(finalI).setRating(newRating);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            JLabel ratingLabel = ImdbLabel("Rating: ");
            ratingPanel.add(ImdbButtonLabelPanel(ratingLabel,ratingButton));
            ratingsPanel.add(ratingPanel);
            JButton  commentButton = ImdbButton(production.getRatings().get(i).getComment(),40);
            commentButton.addActionListener(e->{
                String newComment = GuiConstants.showInputDialog("Enter new comment", production.getRatings().get(finalI).getComment(), 300, 200);
                if(newComment == null) return; // User canceled the input
                production.getRatings().get(finalI).setComment(newComment);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            JLabel commentLabel = ImdbLabel("Comment: ");
            ratingPanel.add(ImdbButtonLabelPanel(commentLabel,commentButton));
            ratingsPanel.add(ratingPanel);
            mainRatingPanel.add(ratingsPanel);

        }
        panel.add(ratingsPanel);
    }

    private void editProductionGenres(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the genres of a production
        int size;
        JLabel genresLabel = ImdbLabel("Genres:");
        JButton addGenreButton = new JButton(GuiConstants.getIcon("add.png", 60,60));
        addGenreButton.addActionListener(e->{
            Genre genre = GuiConstants.selectGenre("choose genre you want to add");
            if(genre == null) return; // User canceled the input
            for (Genre genre1 : production.getGenres()) {
                if (genre1.equals(genre)) {
                    dialogBox("genre already exists");
                    return;
                }

            }
            production.addGenre(genre);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JPanel addGenrePanel = ImdbButtonLabelPanel(genresLabel,addGenreButton);


        JPanel genresPanel = new JPanel(new BorderLayout());
        genresPanel.setLayout(new BoxLayout(genresPanel, BoxLayout.Y_AXIS));
        genresPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        genresPanel.add(addGenrePanel);
        size = production.getGenres().size();
        for(int i=0;i<size;i++){
            JPanel genrePanel = new JPanel(new BorderLayout());
            genrePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 200, 20));
            // make the entries start at the beginning of the line
            genrePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton  genreButton = ImdbButton(String.valueOf(production.getGenres().get(i)),40);
            int finalI = i;
            genreButton.addActionListener(e->{
              Genre genre = GuiConstants.selectGenre("choose genre you want to add");
                if(genre == null) return; // User canceled the input
                production.getGenres().set(finalI, genre);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            genrePanel.add(genreButton);
            genresPanel.add(genrePanel);

        }
        panel.add(genresPanel);
    }

    private void editProductionActors(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the actors of a production
        int size;
        JLabel actorsLabel = ImdbLabel("Actors:");
        JPanel actorsPanel = new JPanel(new BorderLayout());
        actorsPanel.setLayout(new BoxLayout(actorsPanel, BoxLayout.Y_AXIS));
        JButton addActorButton = new JButton(GuiConstants.getIcon("add.png", 60,60));
        addActorButton.addActionListener(e->{
            String actor = GuiConstants.showInputDialog("Enter new actor", "", 300, 200);
            if(actor == null) return; // User canceled the input
            for (String actor1 : production.getActors()) {
                if (actor1.equals(actor)) {
                    dialogBox("actor already exists");
                    return;
                }
            }
            for (Actor actor1 : actors) {
                if (actor1.getName().equals(actor)) {
                    production.addActor(actor);
                    panel.removeAll();
                    panel.add(editMovieDetails(production));
                    panel.revalidate();
                    panel.repaint();
                    return;
                }
            }
            dialogBox("actor doesn't exist in the system");
        });
        JPanel addActorPanel = ImdbButtonLabelPanel(actorsLabel,addActorButton);
        actorsPanel.setLayout(new BoxLayout(actorsPanel, BoxLayout.Y_AXIS));
        actorsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actorsPanel.add(addActorPanel);
        size = production.getActors().size();
        for(int i=0;i<size;i++){
            JPanel actorPanel = new JPanel(new BorderLayout());
            actorPanel.setLayout(new BoxLayout(actorPanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            actorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton  actorButton = ImdbButton(production.getActors().get(i),40);
            int finalI = i;
            actorButton.addActionListener(e->{
                String actor = GuiConstants.showInputDialog("Enter new actor", production.getActors().get(finalI), 300, 200);
                if(actor == null) return; // User canceled the input
                production.getActors().set(finalI,actor);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            actorPanel.add(actorButton);
            actorsPanel.add(actorPanel);

        }
        panel.add(actorsPanel);
    }

    private void editProductionDirectors(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the directors of a production
        JLabel directorsLabel = ImdbLabel("Directors:");
        JButton addDirectorButton = new JButton(GuiConstants.getIcon("add.png", 60,60));
        addDirectorButton.addActionListener(e->{
            String director = GuiConstants.showInputDialog("Enter new director", "", 300, 200);
            if(director == null) return; // User canceled the input
            for (String director1 : production.getDirectors()) {
                if (director1.equals(director)) {
                    dialogBox("director already exists");
                    return;
                }
            }
            production.addDirector(director);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JPanel addDirectorPanel = ImdbButtonLabelPanel(directorsLabel,addDirectorButton);
        JPanel directorsPanel = new JPanel();
        directorsPanel.setLayout(new BoxLayout(directorsPanel, BoxLayout.Y_AXIS));
        directorsPanel.add(addDirectorPanel);
        directorsPanel.setLayout(new BoxLayout(directorsPanel, BoxLayout.Y_AXIS));
        directorsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        int size = production.getDirectors().size();
        for(int i=0;i<size;i++){
            JPanel directorPanel = new JPanel();
            directorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 200, 20));
            directorPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton  directorButton = ImdbButton(production.getDirectors().get(i),40);
            int finalI = i;
            directorButton.addActionListener(e->{
                String director = GuiConstants.showInputDialog("Enter new director", production.getDirectors().get(finalI), 300, 200);
                if(director == null) return; // User canceled the input
                production.getDirectors().set(finalI,director);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            directorPanel.add(directorButton);
            directorsPanel.add(directorPanel);

        }
        panel.add(directorsPanel);
    }

    private void editProductionType(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the type of a production
        JButton typeButton = ImdbButton(production.getType(),40);
        typeButton.addActionListener(e->{
            String type = GuiConstants.chooseType("Choose type" ,"Movie", "Series");
            if(type == null) return; // User canceled the input
            production.setType(type);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();

        });
        JLabel typeLabel = ImdbLabel("Type:");
        JPanel typePanel = ImdbButtonLabelPanel(typeLabel,typeButton);
        // make the entries start at the beginning of the line
        typePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        typePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
        panel.add(typePanel);
    }
    private void editTitle(Production production, JPanel panel) {
        // helper method for editMovieDetails and editSeriesDetails methods to edit the title of a production
        JButton titleButton = ImdbButton(production.getTitle(),40);
        titleButton.addActionListener(e->{
            String title = GuiConstants.showInputDialog("Enter new title", production.getTitle(), 300, 200);
            if(title == null) return; // User canceled the input
            production.setTitle(title);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();

        });
        JLabel titleLabel = ImdbLabel("Title:");
        JPanel titlePanel = ImdbButtonLabelPanel(titleLabel,titleButton);
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));

        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titlePanel);
    }
    //END OF PRODUCTION EDIT FUNCTIONS

    private void dialogBox(String message) {
        // mini dialog box to show messages
        JOptionPane.showMessageDialog(null, message);
    }
    private void dialogArea(String message) {
        // a bigger dialog box to show messages
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 24));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(1000, 800));
        JOptionPane.showMessageDialog(null, scrollPane, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    private JTextField ImdbTextField(int column, int size) {
        // custom text field
        JTextField textField = new JTextField(column);
        textField.setFont(new Font("Arial", Font.BOLD, size));
        return textField;
    }
    private JButton ImdbButton(String text, int size) {
        // custom button
        JButton  button = new  JButton (text);
        button.setFont(new Font("Arial", Font.BOLD, size));
        return button;
    }
    private JLabel ImdbLabel(String text){
        // custom label
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        return label;
    }
    private JPanel ImdbInputPanel(JLabel label, JTextField textField){
        // custom panel with a label and a text field
        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(textField);
        return inputPanel;
    }
    private JPanel ImdbComboPanel(JLabel label, JComboBox<?> comboBox){
        // custom combo box
        JPanel comboPanel = new JPanel();
        comboPanel.add(label);
        comboPanel.add(comboBox);
        return comboPanel;
    }
    private JPanel ImdbButtonLabelPanel(JLabel label, JButton button){
        // custom panel with a button and a label
        JPanel buttonLabelPanel = new JPanel();
        buttonLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 20));
        buttonLabelPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonLabelPanel.add(label);
        buttonLabelPanel.add(button);
        return buttonLabelPanel;
    }
    private JPanel ImdbInputPanelFlow(JLabel label, JTextField textField){
        // custom panel with a label and a text field with flow layout
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(textField);
        return inputPanel;
    }
    private JPanel ImdbLabelScrollablePanel(JLabel label, JScrollPane scrollPane){
        // custom panel with a label and a scroll pane
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(scrollPane);
        return inputPanel;
    }

    private void imdbTextAreaWithBack(JPanel previousPanel , String title, String details) {
        // custom panel with a text area and a back button
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setMargin(new Insets(40, 100, 40, 40));
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 34));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(details);
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(ImdbLabel(title), BorderLayout.CENTER);
        JButton backButton = new JButton(GuiConstants.getIcon("left_arrow.png", 60, 60));
        backButton.addActionListener(e -> setCurrentPanel(previousPanel));
        titlePanel.add(backButton, BorderLayout.WEST);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel .add(titlePanel, BorderLayout.NORTH);
        textAreaPanel .add(scrollPane, BorderLayout.CENTER);
        textAreaPanel .revalidate();
        textAreaPanel.repaint();
        setCurrentPanel(textAreaPanel);
    }
    private User<?> authenticate(String email, String password) {
        // authenticate the user by checking the email and password
        for (User<?> user : users) {
            if (user.getEmail().equals(email)) {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    break;
                }
            }
        }

        return null;
    }
}
