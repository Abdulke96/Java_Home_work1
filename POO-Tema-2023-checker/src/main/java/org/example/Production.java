package org.example;

import java.util.ArrayList;

import java.util.List;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public abstract class Production implements Comparable<Production> {
    protected String title;
    private String type;
    protected List<String> directors;
    protected List<String> actors;
    protected List<Genre> genres;
    protected List<Rating> ratings;
    protected double averageRating;
    private String  plot;
    private String addedBy;
    public Production(){
        this.title = "null";
        this.directors = new ArrayList<>();
        this.actors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.ratings =  new ArrayList<>();
        this.averageRating = 0;
        this.plot = "null";
        this.type = "null";
        this.addedBy = "ADMIN/CONTRIBUTOR";
    }
    public Production(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                      List<Rating> ratings) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.calculateAverageRating();
        this.plot =  "null";
        this.type = type;
        this.addedBy = "ADMIN/CONTRIBUTOR";

    }

    public void calculateAverageRating() {
        if (ratings != null && !ratings.isEmpty()) {
            double sum = 0;
            for (Rating rating : ratings) {
                sum += rating.getValue();
            }
            this.averageRating = sum / ratings.size();
        } else {
            this.averageRating = 0;
        }
    }

    public abstract void displayInfo();
    public abstract String guiDisplay();
    @Override
    public int compareTo(@NotNull Production other) {
            return this.title.compareTo(other.title);

    }

    public void addRating(Rating rating) {
       ratings.add(rating);
      calculateAverageRating();
    }

    public void removeReviewsByUser(Object username) {
        ratings.removeIf(rating -> rating.getUsername().equals(username));
        calculateAverageRating();
    }

    public void displayActor(){
        for(String actor:actors){
            System.out.println("    "+actor);
        }
    }
    public void displayDirectors(){
        for(String dir: directors){
            System.out.println("    "+dir);
        }
    }
    public void displayGenres(){
        if (genres == null) {
            return;
        }
        for(Genre genre: genres){
            System.out.println("    "+genre);
        }
    }

    public void displayRatings(){
        for(Rating rating: ratings){
            rating.displayInfo();
        }
    }

    //GUI helper functions

    public String guiActorsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String actor:actors){
            stringBuilder.append("    ").append(actor).append("\n");
        }
        return stringBuilder.toString();
    }
    public String guiDirectorsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String dir: directors){
            stringBuilder.append("    ").append(dir).append("\n");
        }
        return stringBuilder.toString();
    }
    public String guiGenresString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Genre genre: genres){
            stringBuilder.append("    ").append(genre).append("\n");
        }
        return stringBuilder.toString();
    }
    public String guiRatingsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Rating rating: ratings){
            stringBuilder.append(rating.guiRatingString()).append("\n");
        }
        return stringBuilder.toString();
    }


    public void addDirector(String director) {
        directors.add(director);
    }

    public void addActor(String actor) {
        actors.add(actor);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }
}
