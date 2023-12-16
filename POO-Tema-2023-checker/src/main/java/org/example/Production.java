package org.example;

import java.util.List;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public abstract class Production implements Comparable<Object> {
    ///*****************
    protected String title;//*
    private String type;/////
    protected List<String> directors;//*
    protected List<String> actors;//*
    protected List<Genre> genres;//*
    protected List<Rating> ratings;//*
    protected double averageRating;//*
    private String  plot;



    // Constructor
    public Production(){
        this.title = null;
        this.directors = null;
        this.actors = null;
        this.genres = null;
        this.ratings = null;
        this.averageRating = 0;
        this.plot = null;
        this.type = null;
    }
    public Production(String title, String type, List<String> directors, List<String> actors, List<Genre> genres,
                      List<Rating> ratings) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.calculateAverageRating();
        this.plot =  null;
        this.type = type;

    }

    private void calculateAverageRating() {
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
    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Production otherProduction) {
            return this.title.compareTo(otherProduction.title);
        }
        return 0;
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
        for(Genre genre: genres){
            System.out.println("    "+genre);
        }
    }

    public void displayRatings(){
        for(Rating rating: ratings){
            rating.displayInfo();
        }
    }
}
