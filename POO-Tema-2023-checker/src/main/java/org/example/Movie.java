package org.example;

import java.util.List;
import lombok.*;
import org.constants.WriteOutput;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class Movie extends Production {
    private String duration;
    private int releaseYear;
    public Movie() {
        super();
        this.duration = "null";
        this.releaseYear = 0;
    }
    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                 List<Rating> ratings, String duration, int releaseYear) {
        super(title,type, directors, actors, genres, ratings);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    @Override
    public void displayInfo() {
        WriteOutput.printGreen("Title: " + title);
        WriteOutput.printGreen("Type: "+ getType());
        WriteOutput.printBlue("Directors: ");
        displayDirectors();
        WriteOutput.printBlue("Actors: ");
        displayActor();
        WriteOutput.printBlue("Genres: ");
        displayGenres();
        WriteOutput.printBlue("Ratings: ");
        displayRatings();
        WriteOutput.printGreen("Plot: " + getPlot());
        WriteOutput.printGreen("Average Rating: " + averageRating);
        WriteOutput.printGreen("Duration: " + duration + " minutes");
        WriteOutput.printGreen("Release Year: " + releaseYear);

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
