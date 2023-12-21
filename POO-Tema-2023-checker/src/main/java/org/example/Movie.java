package org.example;

import java.util.List;
import lombok.*;
import org.jetbrains.annotations.NotNull;

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
    public Movie(String title,String type, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String description, String duration, int releaseYear) {
        super(title,type, directors, actors, genres, ratings);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    @Override
    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Type: "+ getType());
        System.out.println("Directors: ");
        displayDirectors();
        System.out.println("Actors: ");
        displayActor();
        System.out.println("Genres: ");
        displayGenres();
        System.out.println("Ratings: ");
        displayRatings();
        System.out.println("Plot: " + getPlot() );
        System.out.println("Average Rating: " + averageRating);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Release Year: " + releaseYear);

    }

    //GUI helper functions
    public String guiDisplay(){
        return "Title: " + title + "\n" +
                "Type: "+ getType() + "\n" +
                "Directors: " + "\n" +
                guiDirectorsString() + "\n" +
                "Actors: " + "\n" +
                guiActorsString() + "\n" +
                "Genres: " + "\n" +
                guiGenresString() + "\n" +
                "Ratings: " + "\n" +
                guiRatingsString() + "\n" +
                "Plot: " + getPlot() + "\n" +
                "Average Rating: " + averageRating + "\n" +
                "Duration: " + duration + " minutes" + "\n" +
                "Release Year: " + releaseYear + "\n";
    }

}
