package org.example;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Series extends Production {
    private final int releaseYear;
    private final int numSeasons;
    private final Map<String, List<Episode>> seasons;

    public Series() {
        super();
        this.releaseYear = 0;
        this.numSeasons = 0;
        this.seasons = null;
    }
    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                  List<Rating> ratings, int releaseYear, int numSeasons,
                  Map<String, List<Episode>> seasons) {
        super(title,type,directors,actors,genres,ratings);
        this.releaseYear = releaseYear;
        this.numSeasons = numSeasons;
        this.seasons = seasons;
    }

    @Override
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Type: "+ getType());
        System.out.println("Directors:");
        displayDirectors();
        System.out.println("Actors: ");
        displayActor();
        System.out.println("Genres: ");
        displayGenres();
        System.out.println("Ratings: ");
        displayRatings();
        System.out.println("Plot: " +getPlot());
        System.out.println("Average Rating: " + averageRating);
        System.out.println("Release Year: " + releaseYear);
        System.out.println("Number of Seasons: " + numSeasons);
        System.out.println("Seasons: ");
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            String seasonName = entry.getKey();
            List<Episode> episodes = entry.getValue();
            System.out.println(seasonName);
            for (Episode episode : episodes) {
                episode.displayInfo();

            }
        }
    }
}

