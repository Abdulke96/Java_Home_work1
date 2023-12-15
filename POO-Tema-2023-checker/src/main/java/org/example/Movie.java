package org.example;

import java.util.List;
import lombok.*;
@EqualsAndHashCode(callSuper = true)
@Data
public class Movie extends Production {
    private final String duration;
    private final int releaseYear;
    public Movie() {
        super();
        this.duration = null;
        this.releaseYear = 0;
    }
    public Movie(String title, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description, String duration, int releaseYear) {
        super(title, directors, actors, genres, ratings, description);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

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
    }
}
