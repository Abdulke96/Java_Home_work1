package org.gui;

import lombok.Getter;
import lombok.Setter;
import org.constants.Constants;
import org.example.IMDB;
import org.example.Production;
import org.example.User;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ApplicationFlowGUI extends JFrame {
    private final User<?> currentUser;
    JPanel mainScreenPanel = new JPanel();
    JPanel menuPanel = new JPanel();
    JPanel changingPhotoPanel = new JPanel();
    private JLabel photoLabel;
    private JPanel photoPanels;
    private int currentPhotoIndex = 1;
    @Getter @Setter
    private boolean homePageFlag = true;

    public ApplicationFlowGUI(User<?> currentUser) {
        this.currentUser = currentUser;
        initializeUI();
    }

    private void initializeUI() {
        configureFrame();
        createMenu();
        createPhotoDisplayAndButtons();  // Change this line
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
    private JTextField createSearchField(){
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        return searchField;
    }
    private JMenu homePage(){
        JMenu homePage = new JMenu("Home Page");
        ImageIcon originalIcon = new ImageIcon(Constants.path +"home.png");
        ImageIcon smallerIcon = new ImageIcon(getScaledImage(originalIcon.getImage(), 60, 60));
        homePage.setIcon(smallerIcon);
        homePage.addActionListener(e -> onHomepage());
        return homePage;
    }
    private boolean onHomepage(){
        setHomePageFlag(true);
        return true;
    }
    private JMenu displayUserAccount(String name){
        JMenu accountMenu = new JMenu(name);
        ImageIcon originalIcon = new ImageIcon(Constants.path +"account.png");
        ImageIcon smallerIcon = new ImageIcon(getScaledImage(originalIcon.getImage(), 60, 60));
        accountMenu.setIcon(smallerIcon);
        List<String > info = Constants.userInfo(currentUser);
        return showMenu(accountMenu, info);
    }

    private JMenu showMenu(JMenu accountMenu, List<String> info) {
        for (int i = 0; i < info.size(); i++) {
            JMenuItem action = new JMenuItem(info.get(i));
            int finalI = i;
            action.addActionListener(e -> performAction(finalI + 1));
            accountMenu.add(action);
        }

        return accountMenu;
    }

    private static Image getScaledImage(Image srcImg, int width, int height) {
        return srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();
       JTextField searchField = createSearchField();
       JMenu homePage = homePage();
        menuPanel.add(searchField);


        JMenu actionsMenu = createActionsMenu();
        // Add the actions menu to the menu bar
        menuBar.add(actionsMenu);
        menuBar.add(homePage);
        menuBar.add(searchField);
        JMenu accountMenu = displayUserAccount(currentUser.getUsername().toString());
        menuBar.add(accountMenu);
        menuBar.setBackground(Color.ORANGE);
        menuBar.setBorderPainted(true);
        menuBar.setFont(new Font("Arial", Font.BOLD, 60));

        // Create a menu for
        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }

    @NotNull
    private JMenu createActionsMenu() {
        JMenu actionsMenu = new JMenu("Actions");
        actionsMenu.addSeparator();
        actionsMenu.setMenuLocation(10, 10);
        actionsMenu.setFont(new Font("Arial", Font.BOLD, 40));
        //add icon
        ImageIcon originalIcon = new ImageIcon(Constants.path +"imdbicon.png");
        ImageIcon smallerIcon = new ImageIcon(getScaledImage(originalIcon.getImage(), 60, 60));
        actionsMenu.setIcon(smallerIcon);
        List<String > actions = Constants.displayOptionsGuide(currentUser);
        return showMenu(actionsMenu, actions);
    }
    private JLabel recommendationLabel(){
        JLabel recommendation = new JLabel("RECOMMENDATION");
        recommendation.setBackground(Color.ORANGE);
        recommendation.setForeground(Color.RED);
        recommendation.setFont(new Font("Arial", Font.BOLD, 40));
        return recommendation;
    }
    private JPanel recommendUser(ImageIcon icon, String title, String description, String duration){
        JPanel recommend = new JPanel(new GridLayout(1,2));
        JPanel photo = new JPanel();
        icon = new ImageIcon(getScaledImage(icon.getImage(), 400, 400));
        photo.add(new JLabel(icon));

        JPanel detail = new JPanel();
        detail.setLayout(new GridLayout(3,1));
        JLabel durationLabel = new JLabel(duration);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 32));
        detail.add(durationLabel);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        detail.add(titleLabel);
        JTextArea descriptionLabel = new JTextArea(description);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        descriptionLabel.setLineWrap(true);
        detail.add(descriptionLabel);

        recommend.add(photo);
        recommend.add(detail);

        return recommend;

    }

    private void createPhotoDisplayAndButtons() {

        photoLabel = new JLabel();
        photoPanels = new JPanel(new GridLayout(6,1));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        photoPanels.setBounds(screenSize.width, screenSize.height,screenSize.width, screenSize.height );

        JLabel recommendation = recommendationLabel();
        photoPanels.add(recommendation);


        ImageIcon icon1 = new ImageIcon(Constants.path+"imdb1.png");
        JPanel recommend = recommendUser(icon1, "film", "this iakakakakkakakakaakakkasaa  a foof fils", "23:50");
        photoPanels.add(recommend);

        JPanel recommend2 = recommendUser(icon1, "fip22lm", "this 22is  a foof fils", "23:250");
        photoPanels.add(recommend2);
        JPanel recommend3 = recommendUser(icon1, "fip22lm", "this 22is  a foof fils", "23:250");
        photoPanels.add(recommend3);
        JPanel recommend4 = recommendUser(icon1, "fip22lm", "this 22is  a foof fils", "23:250");
        photoPanels.add(recommend4);
        JPanel recommend5= recommendUser(icon1, "fip22lm", "this 22is  a foof fils", "23:250");
        photoPanels.add(recommend5);



        loadPhoto();
        JButton leftButton = new JButton("<");
        JButton rightButton = new JButton(">");

        Dimension imageSize = new Dimension(screenSize.width, screenSize.height);
        photoLabel.setPreferredSize(imageSize);
        changingPhotoPanel.add(photoLabel);
        leftButton.addActionListener(e -> showPreviousPhoto());
        rightButton.addActionListener(e -> showNextPhoto());


        mainScreenPanel.setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(leftButton);
        buttonsPanel.add(rightButton);
        mainScreenPanel.add(changingPhotoPanel, BorderLayout.CENTER);
        mainScreenPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainScreenPanel.add(photoPanels, BorderLayout.LINE_END);


        add(mainScreenPanel);
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
            Image scaledImage = icon.getImage().getScaledInstance(photoLabel.getWidth()-10, photoLabel.getHeight()-10, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            photoLabel.setIcon(icon);
        }
    }
    private int displayInformation(){
       JOptionPane.showMessageDialog(null, currentUser);
        return 0;
    }

    private void showNextPhoto() {
        currentPhotoIndex = (currentPhotoIndex % 10) + 1;
        loadPhoto();
    }

    private void showPreviousPhoto() {
        currentPhotoIndex = (currentPhotoIndex - 2 + 10) % 10 + 1;
        loadPhoto();
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

                break;
            case 12:
                break;
            case 13:
                logout();
                break;
            case 14:
                exit();
                break;

            default:
                System.out.println("Action not implemented yet");
        }
    }
    public void removePhotoPanels(){
        getContentPane().remove(changingPhotoPanel);
        revalidate();
        repaint();
    }
    public void viewProductionDetails() {
        removePhotoPanels();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Your Production details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getDimension(screenSize);
      List<Production> productions = IMDB.getInstance().getProductions();
      StringBuilder productionsString = new StringBuilder();
      for (Production production: productions){
          productionsString.append(production.guiDisplay());
      }
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Make JTextArea non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 34)); // Set font for monospaced appearance
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(productionsString.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public void viewActorDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
        setTitle("Your Notifications");
        getDimension(screenSize);
    }

    public void search() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Search for actors/movies/series");
        getDimension(screenSize);
    }

    public void addDeleteFavorites() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Add/Delete actors/movies/series to/from favorites");
        getDimension(screenSize);
    }

    public void addDeleteUser() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Add/Delete user");
        getDimension(screenSize);
    }

    public void addDeleteProductionSystem() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("Add/Delete actor/movie/series/ from system");
        getDimension(screenSize);
    }

    public void updateMovieDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getDimension(screenSize);
    }

    public void updateActorDetails() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getDimension(screenSize);
    }

    public void solveRequests() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        getDimension(screenSize);
    }

    public void logout() {
       getContentPane().remove(mainScreenPanel);
       getContentPane().remove(menuPanel);
       getContentPane().add(photoPanels, BorderLayout.CENTER);
       revalidate();
       repaint();
    }
    public void exit(){
        System.exit(0);
    }

}
