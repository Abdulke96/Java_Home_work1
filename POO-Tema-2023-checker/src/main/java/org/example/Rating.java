package org.example;

public class Rating {
    private String username;
    private int score;
    private String comments;

    // Constructor
    public Rating(String username, int score, String comments) {
        this.username = username;
        this.score = score;
        this.comments = comments;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getComments() {
        return comments;
    }

    // Other methods as needed

    // Example method to display information about the rating
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Score: " + score);
        System.out.println("Comments: " + comments);
        // Display other fields as needed
    }

    public double getValue() {
        return 0;
    }
}

