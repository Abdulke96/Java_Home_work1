package org.example;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.helper.SeriesSeasonsDeserializer;
@EqualsAndHashCode(callSuper = true)
@Data
public class Series extends Production {
    private final int releaseYear;
    private final int numSeasons;
    @JsonDeserialize(using = SeriesSeasonsDeserializer.class)
    private final Map<String, List<Episode>> seasons;

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

        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            String seasonName = entry.getKey();
            List<Episode> episodes = entry.getValue();

            System.out.println("Season: " + seasonName);
            for (Episode episode : episodes) {
                episode.displayInfo();
            }
        }
    }
}

