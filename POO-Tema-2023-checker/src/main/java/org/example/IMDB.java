package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.helper.ActorDeserializer;
import org.helper.ProductionDeserializer;
import org.helper.Constants;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.gui.Auth;

public class IMDB  extends JFrame {
    private static IMDB instance = null;
   private List<User<?>> users;  // User is a base class for Regular, Contributor, and Admin
    public List<Actor> actors; // Actor is a base class for Actor and Director
   private List<Request> requests; // Request is a base class for AddProductionRequest and RemoveProductionRequest
    public List<Production> productions;  // Assuming Production is a base class for Movies and Series
    private User<?> currentUser;  // To keep track of the currently authenticated user
    // Constructor
   private IMDB(List<User<?>> users, List<Actor> actors, List<Request> requests, List<Production> productions) {
        this.users = users;
        this.actors = actors;
        this.requests = requests;
        this.productions = productions;
    }
    public static synchronized IMDB getInstance() {
        if (instance == null) {
            instance = new IMDB(null, null, null, null);
        }
        return instance;
    }


    // Method to run the application
    public void run() {
        loadDataFromJsonFiles();
        authenticateUser();
        startApplicationFlow();
    }
    // Method to load data from JSON files
    private void loadDataFromJsonFiles()  {
        try {
            // Load users from users.json
            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            objectMapper.registerModule(javaTimeModule);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            System.out.println("readig from file"+ Constants.account);
            File usersFile = new File (Constants.account);
            User<?>[] loadedUsers = objectMapper.readValue(usersFile, User[].class);
            this.users = List.of(loadedUsers);
            System.out.println("Users loaded successfully.");
            System.out.println("email: " + users.get(0).getEmail());
            System.out.println("password: " + users.get(0).getPassword());
            // Load actors from actors.json
            System.out.println("readig from file"+ Constants.actors);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Actor.class, new ActorDeserializer());
            objectMapper.registerModule(module);
            File actorsFile = new File(Constants.actors);
            Actor[] loadedActors = objectMapper.readValue(actorsFile, Actor[].class);
            this.actors = List.of(loadedActors);
            System.out.println("Actors loaded successfully.");
            System.out.println("name"+ actors.get(0).getName());
            System.out.println("performanace: " + actors.get(0).getPerformances() );

            System.out.println("readig from file"+ Constants.requests);
            // Load requests from requests.json
            File requestsFile = new File(Constants.requests);
            Request[] loadedRequests = objectMapper.readValue(requestsFile, Request[].class);
            this.requests = List.of(loadedRequests);
            System.out.println("Requests loaded successfully.");
            System.out.println("email: " + requests.get(0).getType());
          // Load productions from production.json
            System.out.println("readig from file"+ Constants.production);
            SimpleModule production = new SimpleModule();
            production.addDeserializer(Production.class, new ProductionDeserializer());
            objectMapper.registerModule(production);
            File productionsFile = new File(Constants.production);
            Production[] loadedProductions = objectMapper.readValue(productionsFile, Production[].class);
            this.productions = List.of(loadedProductions);
            System.out.println("Productions loaded successfully.");
            System.out.println("email: " + productions.get(0).getTitle());

        } catch (IOException e) {
            System.out.println("Error loading data from JSON files: " + e.getMessage());
        }
    }

    // Method to authenticate the user
    private void authenticateUser() {
        System.out.println("Welcome back! Enter your credentials!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("email: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();

        // Perform authentication logic based on the provided username and password
        this.currentUser = authenticate(username, password);
        if (this.currentUser == null) {
            System.out.println("Authentication failed. Exiting the application.");
            System.exit(0);
        } else {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUsername() + "!");
        }
    }

    // Method to start the application flow based on the user's role
    private void startApplicationFlow() {
        if (currentUser instanceof Admin) {
            adminFlow();
        } else if (currentUser instanceof Contributor) {
            contributorFlow();
        } else if (currentUser instanceof Regular) {
            regularFlow();
        }
    }

    // Methods to manage choices for each user role
    private void adminFlow() {
        System.out.println("Admin flow: Manage system data and requests");
        // Implementation for admin flow goes here
    }

    private void contributorFlow() {
        System.out.println("Welcome back user " + currentUser.getEmail() + "!");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());
        System.out.println("Choose an Action:");
        System.out.println("1) View productions details");
        System.out.println("2) view actors details");
        System.out.println("3) view notifications");
        System.out.println("4) search for actors/movies/series");
        System.out.println("5) add/Delete actors/movies/series to/from favorites");
        System.out.println("6) update movie details");
        System.out.println("7) update actor details");
        System.out.println("8) solve requests");
        System.out.println("9) logout");

        // Implementation for contributor flow goes here
    }

    private void regularFlow() {
        System.out.println("Regular user flow: Explore and interact with the system");
        // Implementation for regular user flow goes here

    }

    // Method to perform authentication logic
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
        // Create a new instance of the IMDB class and run the application
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
                // Try to parse as date-time
                return LocalDateTime.parse(dateString, dateTimeFormatter);
            } catch (Exception e) {
                // If parsing as date-time fails, try to parse as date
                return LocalDate.parse(dateString, dateFormatter).atStartOfDay();
            }
        }
    }


}

