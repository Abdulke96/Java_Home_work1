package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request {
    private final RequestTypes type;
    private final LocalDateTime createdDate;
    private final String description;
    private final String username;
    private String to;
    private String actorName;
    private String movieTitle;


    // Constructor
    public Request() {
        this.type = null;
        this.createdDate = LocalDateTime.now();
        this.description = null;
        this.username = null;
        this.to = null;
        this.actorName = null;
        this.movieTitle = null;

    }
    public Request(RequestTypes type, String description, String username) {
        this.type = type;
        this.createdDate = LocalDateTime.now();
        this.description = description;
        this.username = username;
        this.to = determineResolvingUser();
    }

    // Method to automatically determine the resolving user based on the request type
    private String determineResolvingUser() {
        switch (type) {
            case DELETE_ACCOUNT:
            case OTHERS:
                return "ADMIN";
            case ACTOR_ISSUE:
            case MOVIE_ISSUE:
                return username; // Assuming title is the username of the user who added the actor/movie
            default:
                return null; // Handle other cases as needed
        }
    }

    // Method to get the formatted creation date
    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }

    // Getter methods for other fields
    public RequestTypes getType() {
        return type;
    }


    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public String getTo() {
        return to;
    }

    // Other methods as needed
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getActorName() {
        return actorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    // Example method to display information about the request
    public void displayInfo() {
        System.out.println("Request Type: " + type);
        System.out.println("Creation Date: " + getFormattedCreationDate());
        System.out.println("Description: " + description);
        System.out.println("Requesting User: " + username);
        System.out.println("Resolving User: " + to);
        // Display other fields as needed
    }
}
