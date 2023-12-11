package org.example;

import java.util.List;
import java.util.SortedSet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.helper.ProductionDeserializer;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Movie.class, name = "Movie"),
        @JsonSubTypes.Type(value = Series.class, name = "Series"),
        @JsonSubTypes.Type(value = Episode.class, name = "Episode"),
        // Add more types if needed
})
@JsonTypeName("Production")
public abstract class Production implements Comparable<Object> {
    protected String title;//*
    private String types;/////
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
        this.types = null;
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

    }

    // Method to calculate the average rating
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

    // Abstract method to display information specific to each subclass
    public abstract void displayInfo();

    // Method required for sorting productions based on title
    @Override
    public int compareTo(Object o) {
        if (o instanceof Production otherProduction) {
            return this.title.compareTo(otherProduction.title);
        }
        return 0; // Handle other cases as needed
    }

    public void addRating(Rating rating) {
    }

    public void removeReviewsByUser(Object username) {
    }
    ///*****************
    public String getTitle() {
        return title;
    }

    public String getTypes() {
        return types;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageRating() {
        return averageRating;
    }


    public String getPlot() {
        return plot;
    }
}
