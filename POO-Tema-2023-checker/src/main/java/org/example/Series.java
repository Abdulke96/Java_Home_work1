package org.example;

import java.util.List;
import java.util.Map;

public class Series extends Production {
    private int releaseYear;  // Year of release for the series
    private int numSeasons;  // Number of seasons in the series
    private Map<String, List<Episode>> seasons;  // Map of season names to a list of episodes

    // Constructor
    public Series() {
        super();
        this.releaseYear = 0;
        this.numSeasons = 0;
        this.seasons = null;
    }
    public Series(String title, List<String> directors, List<String> actors, List<Genre> genres,
                  List<Rating> ratings, String description, int releaseYear, int numSeasons,
                  Map<String, List<Episode>> seasons) {
        super(title, directors, actors, genres, ratings, description);
        this.releaseYear = releaseYear;
        this.numSeasons = numSeasons;
        this.seasons = seasons;
    }

    // Getter methods for releaseYear and numberOfSeasons
    public int getReleaseYear() {
        return releaseYear;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    // Getter method for seasons
    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    // Override displayInfo method to include Series-specific details
    @Override
    public void displayInfo() {
        System.out.println("Series Title: " + title);
        System.out.println("Directors: " + directors);
        System.out.println("Actors: " + actors);
        System.out.println("Genres: " + genres);
        System.out.println("Description: " + description);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Number of Seasons: " + numSeasons);
        System.out.println("Average Rating: " + averageRating);

        // Display details for each season and its episodes
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            String seasonName = entry.getKey();
            List<Episode> episodes = entry.getValue();

            System.out.println("Season: " + seasonName);

            // Display details for each episode in the season
            for (Episode episode : episodes) {
                episode.displayInfo();
            }
        }
        // Include any other specific details for Series
    }
}

