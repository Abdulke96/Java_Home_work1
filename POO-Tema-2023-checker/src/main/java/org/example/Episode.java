package org.example;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Episode extends Production {
    private final String episodeName;
    private final int duration;
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
    }
}
