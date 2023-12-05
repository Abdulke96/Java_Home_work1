package org.example;

import java.util.List;

public class Movie extends Production {
    private int duration;  // Duration of the movie in minutes
    private int releaseYear;  // Year of release for the movie
    // Add any other optional details specific to a Movie

    // Constructor
    public Movie(String title, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description, int duration, int releaseYear) {
        super(title, directors, actors, genres, ratings, description);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    // Getter methods for duration and releaseYear
    public int getDuration() {
        return duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    // Override displayInfo method to include Movie-specific details
    @Override
    public void displayInfo() {
        System.out.println("Movie Title: " + title);
        System.out.println("Directors: " + directors);
        System.out.println("Actors: " + actors);
        System.out.println("Genres: " + genres);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Average Rating: " + averageRating);
        // Include any other specific details for Movie
    }
}
