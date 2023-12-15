package org.example;

import lombok.Data;
import lombok.Getter;

@Data
public class Rating implements Subject {

    private final String username;
    private final int rating;
    private final String comment;

    public Rating() {
        this.username = null;
        this.rating = 0;
        this.comment = null;
    }
    public Rating(String username, int rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Score: " + rating);
        System.out.println("Comments: " + comment);
    }

    public double getValue() {
        return rating;
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

