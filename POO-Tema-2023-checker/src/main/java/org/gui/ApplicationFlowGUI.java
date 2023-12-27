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

public class ApplicationFlowGUI extends JFrame {
    private final User<?> currentUser;
    List<Actor> actors = IMDB.getInstance().getActors();
    List<Production> productions = IMDB.getInstance().getProductions();
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
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.BOLD, 40));
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
        //Sorting option
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
        JLabel recommendation = new JLabel("RECOMMENDATION");
        recommendation.setBackground(Color.ORANGE);
        recommendation.setForeground(Color.RED);
        recommendation.setFont(new Font("Arial", Font.BOLD, 40));
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
                JButton button = new JButton(production.getTitle());
                button.setFont(new Font("Arial", Font.BOLD, 40));
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
            JButton button = new JButton(production.getTitle());
            button.setFont(new Font("Arial", Font.BOLD, 40));
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
                JButton button = new JButton(actor.getName());
                button.setFont(new Font("Arial", Font.BOLD, 40));
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
            JButton button = new JButton(actor.getName());
            button.setFont(new Font("Arial", Font.BOLD, 40));
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
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 40));

        searchField.setFont(new Font("Arial", Font.BOLD, 40));

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
        JTextField textField = new JTextField(60);
        textField.setFont(new Font("Arial", Font.BOLD, 40));
        JButton deleteButton = new JButton("add/remove");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 40));
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

    }
    private void AddDeleteReview() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Add/Delete a review for a product.");
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

    public void addDeleteUser() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Add/Delete user");
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

    public void addDeleteProductionSystem() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Add/Delete actor/movie/series/ from system");
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

    public void updateMovieDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
    }

    public void updateActorDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getDimension(screenSize);
        setCurrentPanel(mainScreenPanel);
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
    private void dialogBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
