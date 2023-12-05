package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request {
    private RequestTypes requestType;
    private LocalDateTime creationDate;
    private String title;
    private String description;
    private String requestingUser;
    private String resolvingUser;

    // Constructor
    public Request(RequestTypes requestType, String title, String description, String requestingUser) {
        this.requestType = requestType;
        this.creationDate = LocalDateTime.now();
        this.title = title;
        this.description = description;
        this.requestingUser = requestingUser;
        this.resolvingUser = determineResolvingUser();
    }

    // Method to automatically determine the resolving user based on the request type
    private String determineResolvingUser() {
        switch (requestType) {
            case DELETE_ACCOUNT:
            case OTHERS:
                return "ADMIN";
            case ACTOR_ISSUE:
            case MOVIE_ISSUE:
                return title; // Assuming title is the username of the user who added the actor/movie
            default:
                return null; // Handle other cases as needed
        }
    }

    // Method to get the formatted creation date
    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDate.format(formatter);
    }

    // Getter methods for other fields
    public RequestTypes getRequestType() {
        return requestType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRequestingUser() {
        return requestingUser;
    }

    public String getResolvingUser() {
        return resolvingUser;
    }

    // Other methods as needed

    // Example method to display information about the request
    public void displayInfo() {
        System.out.println("Request Type: " + requestType);
        System.out.println("Creation Date: " + getFormattedCreationDate());
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Requesting User: " + requestingUser);
        System.out.println("Resolving User: " + resolvingUser);
        // Display other fields as needed
    }
}
