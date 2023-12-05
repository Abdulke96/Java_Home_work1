package org.example;
import java.util.List;
import java.util.Scanner;

public class IMDB {
    private List<User> users;  // User is a base class for Regular, Contributor, and Admin
    private List<Actor> actors; // Actor is a base class for Actor and Director
    private List<Request> requests; // Request is a base class for AddProductionRequest and RemoveProductionRequest
    private List<Production> productions;  // Assuming Production is a base class for Movies and Series
    private User currentUser;  // To keep track of the currently authenticated user

    // Constructor
    public IMDB(List<User> users, List<Actor> actors, List<Request> requests, List<Production> productions) {
        this.users = users;
        this.actors = actors;
        this.requests = requests;
        this.productions = productions;
    }

    // Method to run the application
    public void run() {
        // Load data from JSON files
        loadDataFromJsonFiles();

        // Authenticate the user
        authenticateUser();

        // Start the application flow based on the user's role
        startApplicationFlow();
    }

    // Method to load data from JSON files
    private void loadDataFromJsonFiles() {
        // Implementation to load data from JSON files goes here
        System.out.println("Loading data from JSON files...");
    }

    // Method to authenticate the user
    private void authenticateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Perform authentication logic based on the provided username and password
        this.currentUser = authenticate(username, password);
        System.out.println("i am here");
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
        System.out.println("Contributor flow: Contribute to the system");
        // Implementation for contributor flow goes here
    }

    private void regularFlow() {
        System.out.println("Regular user flow: Explore and interact with the system");
        // Implementation for regular user flow goes here
    }

    // Method to perform authentication logic
    private User authenticate(String username, String password) {
        // Implement authentication logic based on the provided username and password
        // Return the authenticated user or null if authentication fails
        // Example: Loop through the list of users and check if the provided credentials match any user

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Authentication failed
    }

    public static void main(String[] args) {
        // Create a new instance of the IMDB class and run the application

        IMDB imdb = new IMDB(null, null, null, null);
        imdb.run();

    }
}

