package org.example;

import lombok.Data;
@Data
public class Rating implements Subject {

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



    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(String notification) {

    }

}

