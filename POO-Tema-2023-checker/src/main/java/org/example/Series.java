package org.example;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

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
    //GUI helper functions
    public String guiDisplay(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title: ").append(title).append("\n");
        stringBuilder.append("Type: ").append(getType()).append("\n");
        stringBuilder.append("Directors: ").append("\n");
        stringBuilder.append(guiDirectorsString());
        stringBuilder.append("Actors: ").append("\n");
        stringBuilder.append(guiActorsString());
        stringBuilder.append("Genres: ").append("\n");
        stringBuilder.append(guiGenresString());
        stringBuilder.append("Ratings: ").append("\n");
        stringBuilder.append(guiRatingsString());
        stringBuilder.append("Plot: ").append(getPlot()).append("\n");
        stringBuilder.append("Average Rating: ").append(averageRating).append("\n");
        stringBuilder.append("Release Year: ").append(releaseYear).append("\n");
        stringBuilder.append("Number of Seasons: ").append(numSeasons).append("\n");
        stringBuilder.append("Seasons: ").append("\n");
        for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
            String seasonName = entry.getKey();
            List<Episode> episodes = entry.getValue();
            stringBuilder.append(seasonName).append("\n");
            for (Episode episode : episodes) {
                stringBuilder.append(episode.guiDisplay());
            }
        }
        return stringBuilder.toString();
    }


}

