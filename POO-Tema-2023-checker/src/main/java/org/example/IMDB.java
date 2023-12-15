package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.constants.ApplicationFlow;
import org.constants.UserMode;
import org.gui.ApplicationFlowGUI;
import org.helper.ActorDeserializer;
import org.helper.ProductionDeserializer;
import org.constants.Constants;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.*;
@EqualsAndHashCode(callSuper = true)
@Data
public class IMDB  extends JFrame {
    private static IMDB instance = null;
    private List<User<?>> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private User<?> currentUser;
   private IMDB(List<User<?>> users, List<Actor> actors, List<Request> requests, List<Production> productions) { this.users = (users != null) ? users : new ArrayList<>();
       this.actors = (actors != null) ? actors : new ArrayList<>();
       this.requests = (requests != null) ? requests : new ArrayList<>();
       this.productions = (productions != null) ? productions : new ArrayList<>();
    }
    public static synchronized IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        return instance;
    }
    public void run() {
        loadDataFromJsonFiles();
        System.out.println(this.users.get(0).getEmail());
        System.out.println(this.users.get(0).getPassword());
       int mode = UserMode.chooseUserMode();
      switch (mode) {
            case 1 -> runCli();
            case 2 -> runGui();
            case 3 -> System.exit(0);
        }
    }

    public void runCli() {
        loadDataFromJsonFiles();
       while(this.currentUser==null){
       authenticateUser();
       startApplicationFlow();
       }

    }
    public void runGui() {
        authenticateGUIUser();
    }
    private void loadDataFromJsonFiles()  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
           javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            objectMapper.registerModule(javaTimeModule);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            File usersFile = new File (Constants.account);
            User<?>[] loadedUsers = objectMapper.readValue(usersFile, User[].class);
            this.users = List.of(loadedUsers);

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Actor.class, new ActorDeserializer());
            objectMapper.registerModule(module);
            File actorsFile = new File(Constants.actors);
            Actor[] loadedActors = objectMapper.readValue(actorsFile, Actor[].class);
            this.actors = List.of(loadedActors);


            File requestsFile = new File(Constants.requests);
            Request[] loadedRequests = objectMapper.readValue(requestsFile, Request[].class);
            this.requests = List.of(loadedRequests);
            SimpleModule production = new SimpleModule();
            production.addDeserializer(Production.class, new ProductionDeserializer());
            objectMapper.registerModule(production);
            File productionsFile = new File(Constants.production);
            Production[] loadedProductions = objectMapper.readValue(productionsFile, Production[].class);
            this.productions = List.of(loadedProductions);

        } catch (IOException e) {
            System.out.println("Error loading data from JSON files: " + e.getMessage());
        }
    }

  public void authenticateUser() {
        System.out.println("Welcome back! Enter your credentials!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("email: ");
            String username = scanner.nextLine();
            System.out.print("password: ");
            String password = scanner.nextLine();

            try {
                this.currentUser = authenticate(username, password);
                if (this.currentUser != null) {
                    System.out.println("Authentication successful. Welcome, " + currentUser.getUsername() + "!");
                    break;
                } else {
                    System.out.println("Authentication failed. Retry.");
                }
            } catch (Exception e) {
                System.out.println("Error during authentication: " + e.getMessage());
            }
        }
    }

    private void startApplicationFlow() {
        if (currentUser!=null) {
            ApplicationFlow.appFlow();
        } else {
            System.out.println("No user logged in.");
        }
    }

    private User<?> authenticate(String email, String password) {
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
    public static void main(String[] args) throws IOException {
        IMDB imdb = IMDB.getInstance();
        imdb.run();


    }
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String dateString = p.getText();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            try {
                return LocalDateTime.parse(dateString, dateTimeFormatter);
            } catch (Exception e) {
                return LocalDate.parse(dateString, dateFormatter).atStartOfDay();
            }
        }
    }
    //GUI utility methods
    public void authenticateGUIUser() {
        JFrame frame = new JFrame("Login Form");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height/2;
        screenSize.width = screenSize.width/2;
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocation(screenSize.width/2, screenSize.height/2);
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to IMDB");
        welcomeLabel.setBounds(screenSize.width/2 ,screenSize.height/2, screenSize.width/2, screenSize.height/2);
        JLabel userLabel = new JLabel("Email");
        userLabel.setBounds(screenSize.width/2-100, screenSize.height/2-100, 100, 30);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(screenSize.width/2-100, screenSize.height/2-50, 100, 30);
        JTextField userTextField = new JTextField();
        userTextField.setBounds(screenSize.width/2, screenSize.height/2-100, 150, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(screenSize.width/2, screenSize.height/2-50, 150, 30);
        JCheckBox showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(screenSize.width/2, screenSize.height/2-20, 150, 30);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(screenSize.width/2-100, screenSize.height/2, 100, 30);
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(screenSize.width/2, screenSize.height/2, 100, 30);

        Container container = frame.getContentPane();
        addToContainer(welcomeLabel, userLabel, passwordLabel, userTextField, passwordField, showPassword, loginButton, resetButton, container);

        loginButton.addActionListener(e -> {
            String userText;
            userText = userTextField.getText();
            String pwdText = new String(passwordField.getPassword());
            try {
                this.currentUser = authenticate(userText, pwdText);
                if (this.currentUser != null) {
                   // JOptionPane.showMessageDialog(null, "Authentication successful. Welcome, " + currentUser.getUsername() + "!");
                    frame.dispose();
                   new ApplicationFlowGUI(currentUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Authentication failed. Retry.");
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error during authentication: " + exception.getMessage());
            }
        });

        resetButton.addActionListener(e -> {
            userTextField.setText("");
            passwordField.setText("");
        });

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });


        frame.setVisible(true);


    }

    public static void addToContainer(JLabel welcomeLabel, JLabel userLabel, JLabel passwordLabel, JTextField userTextField, JPasswordField passwordField, JCheckBox showPassword, JButton loginButton, JButton resetButton, Container container) {
        container.add(welcomeLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }


}

