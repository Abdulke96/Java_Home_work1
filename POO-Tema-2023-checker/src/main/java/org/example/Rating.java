package org.example;

public class Rating {
    private String username;
    private int rating;
    private String comment;

    // Constructor
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

    // Getter methods
    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    // Other methods as needed

    // Example method to display information about the rating
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Score: " + rating);
        System.out.println("Comments: " + comment);
        // Display other fields as needed
    }

    public double getValue() {
        return 0;
    }
}

