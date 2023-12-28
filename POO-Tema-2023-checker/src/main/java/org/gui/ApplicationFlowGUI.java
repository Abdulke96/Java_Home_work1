package org.gui;

import org.constants.Constants;
import org.constants.GuiConstants;
import org.example.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationFlowGUI extends JFrame {
    private final User<?> currentUser;
    List<Actor> actors = IMDB.getInstance().getActors();
    List<Production> productions = IMDB.getInstance().getProductions();
    List<User<?>> users = IMDB.getInstance().getUsers();
    List<Request> requests = IMDB.getInstance().getRequests();
    private JLabel photoLabel; // fancy photo display
    private int currentPhotoIndex = 1; // to track the current photo index
    private JPanel currentPanel; //to track the current panel
    JPanel mainScreenPanel = new JPanel(new GridLayout(1, 2));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public ApplicationFlowGUI(User<?> currentUser) {
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

    private void configureFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("IMDB APP");
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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

    private JMenu createActionsMenu() {
        JMenu actionsMenu = new JMenu();
        actionsMenu.addSeparator();
        actionsMenu.setMenuLocation(10, 10);
        actionsMenu.setFont(new Font("Arial", Font.BOLD, 40));
        actionsMenu.setIcon(GuiConstants.getIcon("imdbicon.png", 100, 100));
        List<String> actions = List.of("");
        return showMenu(actionsMenu, actions);
    }
    private JMenu homeMenu() {
        JMenu homeMenu = new JMenu("Home");
        homeMenu.addSeparator();
        homeMenu.setMenuLocation(10, 10);
        homeMenu.setFont(new Font("Arial", Font.BOLD, 80));
        homeMenu.setIcon(GuiConstants.getIcon("home.png", 60, 60));
        List<String> actions = getActions();
        return showMenu(homeMenu, actions);
    }

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

    private JMenu displayUserAccount(String name) {
        JMenu accountMenu = new JMenu(name);
        accountMenu.setIcon(GuiConstants.getIcon("account.png", 60, 60));
        List<String> info = Constants.userInfo(currentUser);
        //TODO change user here to other functions
        return showMenu(accountMenu, info);
    }
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
        //sorting option for the productions
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

    private JPanel recommendUser(ImageIcon icon, String title, String description, String duration) {
        JPanel recommend = new JPanel(new BorderLayout());
        JPanel display = new JPanel(new BorderLayout());
        JPanel recommendationTable = new JPanel(new BorderLayout());
        JPanel photo = new JPanel(new BorderLayout());
        icon = new ImageIcon(GuiConstants.getScaledImage(icon.getImage(), 200, 200));
        photo.add(new JLabel(icon));
         JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));

        JPanel detail = new JPanel();
        detail.add(photo, BorderLayout.WEST);

        detail.setLayout(new FlowLayout());
        JLabel durationLabel = new JLabel(duration);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 24));
        boxPanel.add(durationLabel);
        JButton titleLabel = new JButton(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.addActionListener(e -> {
            for (Production production : productions) {
                if (production.getTitle().equals(title)) {
                    imdbTextArea(display, production.getTitle(), production.guiDisplay());
                }
            }
            setCurrentPanel(display);
        });

        boxPanel.add(titleLabel, BorderLayout.WEST);
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descriptionLabel.setLineWrap(true);
       // boxPanel.add(descriptionLabel, BorderLayout.EAST);
        detail.add(boxPanel);
        recommendationTable.add(detail, BorderLayout.CENTER);
        recommend.add(recommendationTable, BorderLayout.WEST);


        return recommend;
    }

    private void schedulePhotoChange() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showNextPhoto();
            }
        }, 5000, 5000);
    }

    private void loadPhoto() {
        if (photoLabel.getWidth() > 0 && photoLabel.getHeight() > 0) {
            String photoPath = Constants.path + "imdb" + currentPhotoIndex + ".png";
            ImageIcon icon = new ImageIcon(photoPath);
            Image scaledImage = icon.getImage().getScaledInstance(photoLabel.getWidth() - 10, photoLabel.getHeight() - 10, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            photoLabel.setIcon(icon);
        }
    }

    private JLabel recommendationLabel() {
        JLabel recommendation = ImdbLablel("RECOMMENDATION",40);
        recommendation.setBackground(Color.ORANGE);
        recommendation.setForeground(Color.RED);
        return recommendation;
    }


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
        currentPhotoIndex = (currentPhotoIndex % 10) + 1;
        loadPhoto();
    }

    private void showPreviousPhoto() {
        currentPhotoIndex = (currentPhotoIndex - 2 + 10) % 10 + 1;
        loadPhoto();
    }

    private void contributorAction(int action) {
        switch (action) {
            case 0 -> home();
            case 1 -> viewProductionDetails();
            case 2 -> viewActorDetails();
            case 3 -> viewNotifications();
            case 4 -> search();
            case 5 -> addDeleteFavorites();
            case 6 ->createDeleteRequest();
            case 7 -> addDeleteProductionSystem();
            case 8 -> updateMovieDetails();
            case 9 -> updateActorDetails();
            case 10 -> solveRequests();
            case 11 -> logout();
            case 12 -> exit();
        }
    }
    private void adminAction(int action) {
        switch (action) {
            case 0 -> home();
            case 1 -> viewProductionDetails();
            case 2 -> viewActorDetails();
            case 3 -> viewNotifications();
            case 4 -> search();
            case 5 -> addDeleteFavorites();
            case 6 -> addDeleteUser();
            case 7 -> addDeleteProductionSystem();
            case 8 -> updateMovieDetails();
            case 9 -> updateActorDetails();
            case 10 -> solveRequests();
            case 11 -> logout();
            case 12 -> exit();
        }
    }
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
    private void imdbTextArea(JPanel detailsPanel, String title, String details) {
        JTextArea textArea = new JTextArea();
        textArea.setMargin(new Insets(40, 100, 40, 40));
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 34));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(details);

        JScrollPane scrollPane = new JScrollPane(textArea);
        detailsPanel.removeAll();
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

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

                button.addActionListener(e1 -> {
                    imdbTextArea(detailsPanel, production.getTitle(), production.guiDisplay());
                });

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

            button.addActionListener(e -> {
                imdbTextArea(detailsPanel, production.getTitle(), production.guiDisplay());
            });
        }


        JScrollPane scrollPane = new JScrollPane(listPanel);
        productionPanel.add(scrollPane);
        productionPanel.add(detailsPanel);
        setCurrentPanel(productionPanel);
    }


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

                button.addActionListener(e1 -> {
                    imdbTextArea(detailsPanel, actor.getName(), actor.guiDisplay());
                });

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

            button.addActionListener(e -> {
                imdbTextArea(detailsPanel, actor.getName(), actor.guiDisplay());
            });
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        actorPanel.add(scrollPane);
        actorPanel.add(detailsPanel);
        setCurrentPanel(actorPanel);
    }


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
        JTextArea notificationsArea = new JTextArea(notificationsString.toString());
        notificationsArea.setEditable(false);
        notificationsArea.setFont(new Font("Arial", Font.BOLD, 40));
        notificationsPanel.add(notificationsArea, BorderLayout.CENTER);
        setCurrentPanel(notificationsPanel);

    }

    public void search() {
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = ImdbTextField(40,40);
        JButton searchButton = ImdbButton("Search",40);

        searchButton.addActionListener(e -> {
            String search = searchField.getText();
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
    public void addDeleteFavorites() {

        JPanel favoritesPanel = new JPanel(new FlowLayout());
        JTextField textField =ImdbTextField(60,40);
        JButton deleteButton = ImdbButton("ADD/REMOVE",40);
         deleteButton.addActionListener(e -> {
             String search = textField.getText();
            if(search.isEmpty()){
                dialogBox("empty search , please type something to search");
            }else{
               for(Actor actor:actors){
                   if (actor.getName().equals(search)){
                       if(currentUser.getFavoriteActors().contains(search)){
                           currentUser.getFavoriteActors().remove(search);
                           dialogBox("item removed from favorite");
                           return;
                       }else {
                           currentUser.addToFavoriteActors(actor);
                           dialogBox("item added to favorite");
                       }
                   }
               }
                for(Production product:productions){
                    if ( product.getTitle().equals(search)){
                        if(currentUser.getFavoriteProductions().contains(search)){
                            currentUser.getFavoriteProductions().remove(search);
                           dialogBox("item removed from favorite");
                            return;
                        }else {
                            currentUser.addToFavoriteProductions(product);
                            dialogBox("item added to favorite");
                            return;
                        }
                    }
                }

                dialogBox("item not found in the sytem");
            }

         });
         favoritesPanel.add(textField);
         favoritesPanel.add(deleteButton);

        setCurrentPanel(favoritesPanel);
    }
    private void createDeleteRequest() {
        JPanel createDeleteRequestPanel = new JPanel(new FlowLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS ));

        JTextField descriptionField = new JTextField(40);
        JComboBox<String> requestTypeComboBox = new JComboBox<>(new String[]{"DELETE_ACCOUNT", "ACTOR_ISSUE", "MOVIE_ISSUE", "OTHERS"});
        requestTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
         JTextField toField = ImdbTextField(40,40);
         JLabel descriptionLabel = ImdbLablel("Description: ",40);
         JLabel toLabel = ImdbLablel("Request To:  ",40);
         JLabel requestLabel = ImdbLablel("Request Type:              ",40);

         JPanel firstField =ImdbInputPanel( descriptionLabel,descriptionField);
         mainPanel.add(firstField);

         JPanel thirdFiled =  ImdbInputPanel(toLabel,toField);
        mainPanel.add(thirdFiled);

         JPanel secondFiled =  ImdbComboPanel(requestLabel,requestTypeComboBox);
         mainPanel.add(secondFiled);


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

            String description = descriptionField.getText();
            String requestTo = toField.getText();
            Request request = new Request(requestTypes,description,requestTo);
            IMDB.getInstance().getRequests().add(request);
          dialogBox("added to system");

        });
        JButton deleteButton = ImdbButton("Delete Request",40);
        deleteButton.addActionListener(e->{

        });
        buttons.add(createButton);
        buttons.add(deleteButton);
        mainPanel.add(buttons);

        createDeleteRequestPanel.add(mainPanel);

         setCurrentPanel(createDeleteRequestPanel);


    }
    private void AddDeleteReview() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Add/Delete a review for a product.");
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

    public void addDeleteUser() {
        setTitle("Add/Delete user");
        getDimension(screenSize);
        JPanel addDeleteUserPanel = new JPanel(new FlowLayout());
        JPanel addUserPanel =  new JPanel(new FlowLayout());
        JComboBox<String> operationTypeComboBox = new JComboBox<>(new String[]{"DELETE USER", "ADD USER"});
        operationTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
        chooseOperations(operationTypeComboBox, addUserPanel);
        addDeleteUserPanel.add(operationTypeComboBox);


        JTextField nameField = ImdbTextField(40,40);
        JLabel nameLabel = ImdbLablel("Full Name: ",40);
        JPanel namePanel = ImdbInputPanel(nameLabel,nameField);
        addUserPanel.add(namePanel);

        //userType
        JComboBox<String> userTypeComboBox = new JComboBox<>(new String[]{"ADMIN USER", "CONTRIBUTOR", "REGULAR USER"});
        userTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
        JLabel userTypeLabel = ImdbLablel("User type :",40);
        JPanel userTypePanel = ImdbComboPanel(userTypeLabel,userTypeComboBox);
        Admin<?> admin = new Admin<>();
        User<?> newUser;
        AtomicInteger choice = new AtomicInteger(userTypeComboBox.getSelectedIndex());
        userTypeComboBox.addActionListener(e -> {
            choice.set(userTypeComboBox.getSelectedIndex());
        });
        String name = nameField.getText();
        if (!GuiConstants.isFullName(name)){
            while (!GuiConstants.isFullName(name)){
                dialogBox("Please enter a valid name");
                name = nameField.getText();
            }
        }
        AccountType accountType = GuiConstants.convertToAccountType(choice.get());
        addUserPanel.add(userTypePanel);
        newUser  = admin.addUser(name, accountType);
        newUser.setName(name);

       // newUser.setUsername(FunctionsFactory.generateUniqueUsername(name));
        users.add(newUser);
        setCurrentPanel(addDeleteUserPanel);
    }

    private void chooseOperations(JComboBox<String> operationTypeComboBox, JPanel addUserPanel) {
        operationTypeComboBox.addActionListener(e -> {
            if (operationTypeComboBox.getSelectedIndex() == 0) {
                String username = JOptionPane.showInputDialog("Enter username");
                if(username == null) return; // User canceled the input
                for (User<?> user : users) {
                    if (user.getUsername().equals(username)) {
                        users.remove(user);
                        //delete rating by the user
                        for (Production production : productions) {
                            production.getRatings().removeIf(rating -> rating.getUsername().equals(user.getUsername()));
                        }
                        //delete requests by the user
                        requests.removeIf(request -> request.getUsername().equals(user.getUsername()));

                        dialogBox("User"+ user.getName()+ " deleted from the system");
                        return;
                    }
                }
                dialogBox("User not found in the system");

            } else if (operationTypeComboBox.getSelectedIndex() == 1) {
                //nam
                setCurrentPanel(addUserPanel);


            }
        });
    }

    public void addDeleteProductionSystem() {
        setTitle("Add/Delete actor/movie/series/ from system");
        getDimension(screenSize);
        JPanel addDeleteProductionPanel = new JPanel(new FlowLayout());
        JPanel addProductionPanel =  new JPanel(new FlowLayout());
        JComboBox<String> operationTypeComboBox = new JComboBox<>(new String[]{"DELETE PRODUCTION", "ADD PRODUCTION"});
        operationTypeComboBox.setFont(new Font("Arial", Font.BOLD, 40));
        operationTypeComboBox.addActionListener(e -> {
            if (operationTypeComboBox.getSelectedIndex() == 0) {
                String title = JOptionPane.showInputDialog("Enter title");
                if(title == null) return; // User canceled the input
                for (Production production : productions) {
                    if (production.getTitle().equals(title)) {
                        productions.remove(production);
                        dialogBox("Production"+ production.getTitle()+ " deleted from the system");
                        return;
                    }
                }
                dialogBox("Production not found in the system");

            } else if (operationTypeComboBox.getSelectedIndex() == 1) {

                setCurrentPanel(addProductionPanel);

            }
        });

        addDeleteProductionPanel.add(operationTypeComboBox);



        setCurrentPanel(addDeleteProductionPanel);
    }

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
        JLabel durationLabel = ImdbLablel("Duration:",40);
        JPanel durationPanel = ImdbButtonLabelPanel(durationLabel,durationButton);
        // make the entries start at the beginning of the line
        durationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(durationPanel);

        //release year
       editProductionRealeaseYear(production, panel);


       return panel;
   }



    private JPanel editSeriesDetails(Production production){
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
        editProductionRealeaseYear(production, panel);
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
        JLabel seasonsLabel = ImdbLablel("Number of seasons:",40);
        JPanel seasonsPanel = ImdbButtonLabelPanel(seasonsLabel,seasonsButton);
        // make the entries start at the beginning of the line
        seasonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(seasonsPanel);

        // season details




        return panel;
  }
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
               JPanel editPanel = editDetails(actor);
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
    private JPanel editDetails(Actor actor ){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // make the entries start at the beginning of the line
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton editButton = ImdbButton(actor.getName(),40);
        editButton.addActionListener(e->{
            String name = GuiConstants.showInputDialog("Enter new name", actor.getName(), 300, 200);
            if(name == null) return; // User canceled the input
            actor.setName(name);
            panel.removeAll();
            panel.add(editDetails(actor));
            panel.revalidate();
            panel.repaint();

        });
        panel.add(editButton);
        JLabel nameLabel = ImdbLablel("Performance:",40);
         nameLabel.setBackground(Color.ORANGE);
        panel.add(nameLabel);
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
                panel.add(editDetails(actor));
                panel.revalidate();
                panel.repaint();
            });
             JButton typeButton = ImdbButton(actor.getPerformances().get(i).getValue(),40);
            typeButton.addActionListener(e->{
                String type =  GuiConstants.showInputDialog("Enter new type", actor.getPerformances().get(finalI).getValue(), 300, 200);
                if(type == null) return; // User canceled the input
                actor.setValue(actor.getPerformances().get(finalI).getKey(),type);
                panel.removeAll();
                panel.add(editDetails(actor));
                panel.revalidate();
                panel.repaint();
            });
            performancePanel.add(titleButton);
            performancePanel.add(typeButton);
            panel.add(performancePanel);


        }


        JButton biographyButton = ImdbButton("biography",40);
        biographyButton.addActionListener(e->{
            String biography = GuiConstants.showInputDialog("Enter new biography", actor.getBiography(), 600, 400);
            if(biography == null) return; // User canceled the input
            actor.setBiography(biography);
            panel.removeAll();
            panel.add(editDetails(actor));
            panel.revalidate();
            panel.repaint();
        });
        panel.add(biographyButton);
        JTextArea biographyArea = new JTextArea(GuiConstants.breakString(actor.getBiography(), 50));
        biographyArea.setEditable(false);
        biographyArea.setFont(new Font("Arial", Font.BOLD, 40));

        panel.add(biographyArea);

        return panel;



    }

    public void solveRequests() {
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

   public void logout() {
            getDimension(screenSize);
       Auth auth = new Auth();
       setVisible(false);
       auth.setVisible(true);
        }
    public void exit(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Your Actor details");
        setForeground(Color.black);
        setBackground(Color.gray);
        setSize(screenSize.width, screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCurrentPanel(mainScreenPanel);
    }
   private void home(){
         setCurrentPanel(mainScreenPanel);
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

    //PRODUCTION EDIT FUNCTIONS
    private void editProductionRealeaseYear(Production production, JPanel panel) {

        JButton releaseDateButton = ImdbButton("",40);
          if (production instanceof Series){
              releaseDateButton = ImdbButton(String.valueOf(((Series) production).getReleaseYear()),40);
          } else if (production instanceof Movie){
              releaseDateButton = ImdbButton(String.valueOf(((Movie) production).getReleaseYear()),40);
          }
        releaseDateButton.addActionListener(e->{
            String releaseDate = GuiConstants.showInputDialog("Enter new release date", String.valueOf(((Movie) production).getReleaseYear()), 300, 200);
            if(releaseDate == null) return; // User canceled the input
            int releaseYear;
            try {
                releaseYear = Integer.parseInt(releaseDate);
                if(releaseYear<1900 || releaseYear>2021){
                    dialogBox("release year should be between 1900 and 2021");
                    return;
                }
            } catch (NumberFormatException numberFormatException) {
                dialogBox("release year should be a number");
                return;
            }
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
        JLabel releaseDateLabel = ImdbLablel("Release Date:",40);
        JPanel releaseDatePanel = ImdbButtonLabelPanel(releaseDateLabel,releaseDateButton);
        // make the entries start at the beginning of the line
        releaseDatePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(releaseDatePanel);
    }

    private void editProductionPlot(Production production, JPanel panel) {
        JButton plotButton = ImdbButton("plot",40);
        plotButton.addActionListener(e->{
            String plot = GuiConstants.showInputDialog("Enter new plot", production.getPlot(), 600, 400);
            if(plot == null) return; // User canceled the input
            production.setPlot(plot);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();
        });
        JLabel plotLabel = ImdbLablel("Plot:",40);
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
        int size;
        JLabel ratingsLabel = ImdbLablel("Ratings:",40);
        JPanel ratingsPanel = new JPanel(new BorderLayout());
        ratingsPanel.setLayout(new BoxLayout(ratingsPanel, BoxLayout.Y_AXIS));
        ratingsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        ratingsPanel.add(ratingsLabel);
        size = production.getRatings().size();
        for(int i=0;i<size;i++){
            JPanel mainRatingPanel = new JPanel(new BorderLayout());
            mainRatingPanel.setLayout(new BoxLayout(mainRatingPanel, BoxLayout.Y_AXIS));
            mainRatingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JPanel ratingPanel = new JPanel(new BorderLayout());
            ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            ratingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JLabel usernameLabel = ImdbLablel("Username: ",40);
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
                String newRating = GuiConstants.showInputDialog("Enter new rating", String.valueOf(production.getRatings().get(finalI).getRating()), 300, 200);
                if(newRating == null ) return; // User canceled the input
                int rating;
                try {
                    rating = Integer.parseInt(newRating);
                    if(rating<1 || rating>10){
                        dialogBox("rating should be between 1 and 10");
                        return;
                    }
                } catch (NumberFormatException numberFormatException) {
                    dialogBox("rating should be a number");
                    return;
                }
                production.getRatings().get(finalI).setRating(rating);
                panel.removeAll();
                panel.add(editMovieDetails(production));
                panel.revalidate();
                panel.repaint();
            });
            JLabel ratingLabel = ImdbLablel("Rating: ",40);
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
            JLabel commentLabel = ImdbLablel("Comment: ",40);
            ratingPanel.add(ImdbButtonLabelPanel(commentLabel,commentButton));
            ratingsPanel.add(ratingPanel);
            mainRatingPanel.add(ratingsPanel);

        }
        panel.add(ratingsPanel);
    }

    private void editProductionGenres(Production production, JPanel panel) {
        int size;
        JLabel genresLabel = ImdbLablel("Genres:",40);
        JPanel genresPanel = new JPanel(new BorderLayout());
        genresPanel.setLayout(new BoxLayout(genresPanel, BoxLayout.Y_AXIS));
        genresPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        genresPanel.add(genresLabel);
        size = production.getGenres().size();
        for(int i=0;i<size;i++){
            JPanel genrePanel = new JPanel(new BorderLayout());
            genrePanel.setLayout(new BoxLayout(genrePanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
            genrePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            JButton  genreButton = ImdbButton(String.valueOf(production.getGenres().get(i)),40);
            int finalI = i;
            genreButton.addActionListener(e->{
                String genre = GuiConstants.showInputDialog("Enter new genre", String.valueOf(production.getGenres().get(finalI)), 300, 200);
                if(genre == null) return; // User canceled the input
                production.getGenres().set(finalI, Genre.valueOf(genre));
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
        int size;
        JLabel actorsLabel = ImdbLablel("Actors:",40);
        JPanel actorsPanel = new JPanel(new BorderLayout());
        actorsPanel.setLayout(new BoxLayout(actorsPanel, BoxLayout.Y_AXIS));
        actorsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actorsPanel.add(actorsLabel);
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
        JLabel directorsLabel = ImdbLablel("Directors:",40);
        JPanel directorsPanel = new JPanel(new BorderLayout());
        directorsPanel.setLayout(new BoxLayout(directorsPanel, BoxLayout.Y_AXIS));
        directorsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        directorsPanel.add(directorsLabel);
        int size = production.getDirectors().size();
        for(int i=0;i<size;i++){
            JPanel directorPanel = new JPanel(new BorderLayout());
            directorPanel.setLayout(new BoxLayout(directorPanel, BoxLayout.Y_AXIS));
            // make the entries start at the beginning of the line
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
        JButton typeButton = ImdbButton(production.getType(),40);
        typeButton.addActionListener(e->{
            String type = GuiConstants.showInputDialog("Enter new type", production.getType(), 300, 200);
            if(type == null) return; // User canceled the input
            production.setType(type);
            panel.removeAll();
            panel.add(editMovieDetails(production));
            panel.revalidate();
            panel.repaint();

        });
        JLabel typeLabel = ImdbLablel("Type:",40);
        JPanel typePanel = ImdbButtonLabelPanel(typeLabel,typeButton);
        // make the entries start at the beginning of the line
        typePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(typePanel);
    }
    private void editTitle(Production production, JPanel panel) {
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
        JLabel titleLabel = ImdbLablel("Title:",40);
        JPanel titlePanel = ImdbButtonLabelPanel(titleLabel,titleButton);
        //make the entries start at the beginning of the line
        titlePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titlePanel);
    }

    private void dialogBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // SOME helper fanctions
    private JTextField ImdbTextField(int column, int size) {
        JTextField textField = new JTextField(column);
        textField.setFont(new Font("Arial", Font.BOLD, size));
        return textField;
    }

    private JButton ImdbButton(String text, int size) {
        JButton  button = new  JButton (text);
        button.setFont(new Font("Arial", Font.BOLD, size));
        return button;
    }
    private JLabel ImdbLablel(String text, int size){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, size));
        return label;
    }
    private JPanel ImdbInputPanel(JLabel label, JTextField textField){
        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(textField);
        return inputPanel;
    }
    private JPanel ImdbComboPanel(JLabel label, JComboBox<?> comboBox){
        JPanel comboPanel = new JPanel();
        comboPanel.add(label);
        comboPanel.add(comboBox);
        return comboPanel;
    }
    private JPanel ImdbButtonLabelPanel(JLabel label, JButton button){
        JPanel buttonLabelPanel = new JPanel();
       // buttonLabelPanel.setLayout(new BoxLayout(buttonLabelPanel, BoxLayout.Y_AXIS));

        buttonLabelPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonLabelPanel.add(label);
        buttonLabelPanel.add(button);
        return buttonLabelPanel;
    }

    // edit Production  functions

}
