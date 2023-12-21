package org.example;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Request implements Subject{
    private final RequestTypes type;
    private final LocalDateTime createdDate;
    private final String description;
    private final String username;
    private String to;
    private String actorName;
    private String movieTitle;

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

    private String determineResolvingUser() {
        return switch (type) {
            case DELETE_ACCOUNT, OTHERS -> "ADMIN";
            case ACTOR_ISSUE, MOVIE_ISSUE -> "CONTRIBUTOR/ADMIN";
            default -> null;
        };
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }

    public void displayInfo() {
        System.out.println("Request Type: " + type);
        System.out.println("Creation Date: " + getFormattedCreationDate());
        System.out.println("Description: " + description);
        System.out.println("Requesting User: " + username);
        System.out.println("Resolving User: " + to);
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(String notification) {

    }
}
