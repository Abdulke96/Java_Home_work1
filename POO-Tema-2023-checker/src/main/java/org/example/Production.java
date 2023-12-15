package org.example;

import java.util.List;
import java.util.SortedSet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import org.helper.ProductionDeserializer;
import lombok.Data;
import org.jetbrains.annotations.NotNull;


@Getter
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Movie.class, name = "Movie"),
//        @JsonSubTypes.Type(value = Series.class, name = "Series"),
//        @JsonSubTypes.Type(value = Episode.class, name = "Episode"),
//})
//@JsonTypeName("Production")
@Data
public abstract class Production implements Comparable<Object> {
    ///*****************
    protected String title;//*
    private String type;/////
    protected List<String> directors;//*
    protected List<String> actors;//*
    protected List<Genre> genres;//*
    protected List<Rating> ratings;//*
    protected String description;//*
    protected double averageRating;//*
    private String  plot;



    // Constructor
    public Production(){
        this.title = null;
        this.directors = null;
        this.actors = null;
        this.genres = null;
        this.ratings = null;
        this.description = null;
        this.averageRating = 0;
        this.plot = null;
        this.type = null;
    }
    public Production(String title, List<String> directors, List<String> actors, List<Genre> genres,
                      List<Rating> ratings, String description) {
        this.title = title;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.description = description;
        this.calculateAverageRating();
        this.plot = null;
        this.type = null;

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
//        ratings.add(rating);
//        calculateAverageRating();
    }

    public void removeReviewsByUser(Object username) {
//        ratings.removeIf(rating -> rating.getUsername().equals(username));
//        calculateAverageRating();
    }


}
