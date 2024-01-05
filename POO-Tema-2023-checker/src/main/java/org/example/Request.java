package org.example;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.constants.RequestStatus;
import org.constants.WriteOutput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class Request implements Subject{
    private RequestTypes type;
    private LocalDateTime createdDate;
    private String description;
    private String username;
    private String to;
    private String actorName;
    private String movieTitle;
    // additional fields
    @Getter @Setter
    RequestStatus status;
    List<Observer> observers = new ArrayList<>();

    public Request() {
        this.type = RequestTypes.OTHERS;
        this.createdDate = LocalDateTime.now();
        this.description = "null";
        this.username = "null";
        this.to = "ADMIN";
        this.actorName = "null";
        this.movieTitle = "null";
        this.status = RequestStatus.Pending;


    }
    public Request(RequestTypes type, String description, String username) {
        this.type = type;
        this.createdDate = LocalDateTime.now();
        this.description = description;
        this.username = username;
        this.to = determineResolvingUser();
        this.actorName = "null";
        this.movieTitle = "null";
        this.status = RequestStatus.Pending;
    }

    private String determineResolvingUser() {
        return switch (type) {
            case DELETE_ACCOUNT, OTHERS -> "ADMIN";
            case ACTOR_ISSUE, MOVIE_ISSUE -> "CONTRIBUTOR/ADMIN";
        };
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }

    public void displayInfo() {
        WriteOutput.printGreen("Request Type: " + type);
        WriteOutput.printGreen("Status: " + status);
        WriteOutput.printGreen("Creation Date: " + getFormattedCreationDate());
        WriteOutput.printGreen("Description: " + description);
        WriteOutput.printGreen("Requesting User: " + username);
        WriteOutput.printGreen("Resolving User: " + to);
        WriteOutput.printGreen("Actor Name: " + actorName);
        WriteOutput.printGreen("Movie Title: " + movieTitle);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers(String notification) {
        for (Observer observer : observers) {
            observer.update(notification);
        }
    }



}
