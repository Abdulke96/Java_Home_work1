package org.example;

import java.util.List;

public abstract class Production implements Comparable<Object> {
    protected String title;
    protected List<String> directors;
    protected List<String> actors;
    protected List<Genre> genres;
    protected List<Rating> ratings;
    protected String description;
    protected double averageRating;

    // Constructor
    public Production(String title, List<String> directors, List<String> actors, List<Genre> genres,
                      List<Rating> ratings, String description) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.description = description;
        this.calculateAverageRating();
    }

    // Method to calculate the average rating
    private void calculateAverageRating() {
        if (ratings != null && !ratings.isEmpty()) {
            double sum = 0;
            for (Rating rating : ratings) {
                sum += rating.getValue();
            }
            this.averageRating = sum / ratings.size();
        } else {
            this.averageRating = 0;
        }
    }

    // Abstract method to display information specific to each subclass
    public abstract void displayInfo();

    // Method required for sorting productions based on title
    @Override
    public int compareTo(Object o) {
        if (o instanceof Production) {
            Production otherProduction = (Production) o;
            return this.title.compareTo(otherProduction.title);
        }
        return 0; // Handle other cases as needed
    }

    public void addRating(Rating rating) {
    }

    public void removeReviewsByUser(Object username) {
    }
}
