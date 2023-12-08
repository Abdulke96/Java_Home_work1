package org.example;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

public class Episode extends Production {
    private final String episodeName;  // Name of the episode
    private final int duration;  // Duration of the episode in minutes
    // Add any other optional details specific to an Episode

    // Constructor
    public Episode() {
        super();
        this.episodeName = null;
        this.duration = 0;
    }
    public Episode(String title, List<String> directors, List<String> actors, List<Genre> genres,
                   List<Rating> ratings, String description, String episodeName, int duration) {
        super(title, directors, actors, genres, ratings, description);
        this.episodeName = episodeName;
        this.duration = duration;
    }

    // Getter methods for episodeName and duration
    public String getEpisodeName() {
        return episodeName;
    }

    public int getDuration() {
        return duration;
    }

    // Override displayInfo method to include Episode-specific details
    @Override
    public void displayInfo() {
        System.out.println("Episode Title: " + title);
        System.out.println("Episode Name: " + episodeName);
        System.out.println("Directors: " + directors);
        System.out.println("Actors: " + actors);
        System.out.println("Genres: " + genres);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Average Rating: " + averageRating);
        // Include any other specific details for Episode
    }
}
