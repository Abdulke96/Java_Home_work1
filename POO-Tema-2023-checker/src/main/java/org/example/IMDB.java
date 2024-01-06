package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.constants.ApplicationFlow;
import org.constants.ReadInput;
import org.constants.WriteOutput;
import org.gui.ApplicationFlowGUI;
import org.helper.ActorDeserializer;
import org.helper.ProductionDeserializer;
import org.constants.Constants;

import javax.swing.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.*;
@EqualsAndHashCode(callSuper = true)
@Data
public class IMDB  extends JFrame {
    /**
     * list for storing data
     */
    private static IMDB instance = null;
    private List<User<?>> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    private User<?> currentUser;

    private IMDB(List<User<?>> users, List<Actor> actors, List<Request> requests, List<Production> productions) {
       this.users = (users != null) ? users : new ArrayList<>();
       this.actors = (actors != null) ? actors : new ArrayList<>();
       this.requests = (requests != null) ? requests : new ArrayList<>();
       this.productions = (productions != null) ? productions : new ArrayList<>();
    }

    /**
     * This method is used to get the instance of the IMDB class.
     * @return IMDB
     */
    public static synchronized IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        return instance;
    }

    /**
     * This method is used to run the application.
     */
    public void run() {
        loadDataFromJsonFiles();
       int mode = ReadInput.chooseUserMode();
      switch (mode) {
            case 1 -> runCli();
            case 2 -> runGui();
            case 3 -> System.exit(0);
        }
    }
    /**
     * This method is used to run the command line interface.
     */

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
    /**
     * This method is used to load data from JSON files.
     */
    private void loadDataFromJsonFiles()  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
           javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            objectMapper.registerModule(javaTimeModule);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            File usersFile = new File (Constants.account);
            User<?>[] loadedUsers = objectMapper.readValue(usersFile, User[].class);
            this.users = new ArrayList<>(Arrays.asList(loadedUsers));

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Actor.class, new ActorDeserializer());
            objectMapper.registerModule(module);
            File actorsFile = new File(Constants.actors);
            Actor[] loadedActors = objectMapper.readValue(actorsFile, Actor[].class);
            this.actors = new ArrayList<>(Arrays.asList(loadedActors));


            File requestsFile = new File(Constants.requests);
            Request[] loadedRequests = objectMapper.readValue(requestsFile, Request[].class);
            this.requests = new ArrayList<>(Arrays.asList(loadedRequests));
            SimpleModule production = new SimpleModule();
            production.addDeserializer(Production.class, new ProductionDeserializer());
            objectMapper.registerModule(production);
            File productionsFile = new File(Constants.production);
            Production[] loadedProductions = objectMapper.readValue(productionsFile, Production[].class);
            this.productions = new ArrayList<>(Arrays.asList(loadedProductions));

        } catch (IOException e) {
            System.out.println("Error loading data from JSON files: " + e.getMessage());
        }
    }

    /**
     * This method is used to authenticate a user.
     */
  public void authenticateUser() {
      WriteOutput.makeBreak()  ;
      WriteOutput.printBlue("Welcome back! Enter your credentials!");
      WriteOutput.makeBreak();
        while (true) {
            WriteOutput.printBlue("Email: ");
            String username = ReadInput.readLine();
            WriteOutput.printBlue("Password: ");
            String password = ReadInput.readLine();

            try {
               this.currentUser = authenticate(username, password);
                if (this.currentUser != null) {
                    WriteOutput.printGreen("Authentication successful. Welcome, " + currentUser.getUsername() + "!");
                    break;
                } else {
                    WriteOutput.printRed("Authentication failed. Retry.");
                }
            } catch (Exception e) {
                WriteOutput.printRed("Error during authentication: " + e.getMessage());
            }
        }
    }

    /**
     * This method is used to start the application flow.
     */
    private void startApplicationFlow() {
        if (currentUser!=null) {
            if (!(currentUser instanceof Regular<?>)) {
                Staff<?> staff = (Staff<?>) currentUser;
                WriteOutput.printBlue("Favorite productions: ");
                WriteOutput.printBlueLine("[");
                for (String production : staff.getFavoriteProductions()) {
                    WriteOutput.printGreenLine(production + ", ");
                }
                WriteOutput.printBlueLine(" ]\n");
                WriteOutput.printBlue("production Contribution: ");
                WriteOutput.printBlueLine("[ ");
                for (String production : staff.getProductionsContribution()) {
                    WriteOutput.printGreenLine(production + ", ");
                }
                WriteOutput.printBlueLine("]\n");
            };
            ApplicationFlow.appFlow();
        } else {
            WriteOutput.printRed("No user logged in.");
        }
    }

    /**
     * This method is used to authenticate a user.
     * @param email String
     * @param password String
     * @return User<?>
     */
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

    /**
     * This method is used to run the main method.
     */
    public static void main(String[] args) {
        IMDB imdb = IMDB.getInstance();
        imdb.run();
    }

    /**
     * This static class is used to deserialize a LocalDateTime object.
     */
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
    /**
     * This method is used to authenticate a user.
     */
    public void authenticateGUIUser() {
        new ApplicationFlowGUI();
    }


}

