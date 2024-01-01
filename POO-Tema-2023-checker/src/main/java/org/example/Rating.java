package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Rating implements Subject {
 List<Observer> observers = new ArrayList<>();
    private  String username;
    private  int rating;
    private  String comment;

    public Rating() {
        this.username = "null";
        this.rating = 0;
        this.comment = "null";
    }
    public Rating(String username, int rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }
    public void displayInfo() {
        System.out.println("    Username: " + username);
        System.out.println("    Score: " + rating);
        System.out.println("    Comments: " + comment);
    }
    //GUI helper functions
    public String guiRatingString(){
        return "    Username: " + username + "\n" +
                "    Score: " + rating + "\n" +
                "    Comments: " + comment + "\n";
    }

    public double getValue() {
        return rating;
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

