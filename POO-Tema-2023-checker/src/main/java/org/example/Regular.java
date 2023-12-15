package org.example;

import java.util.List;

public class Regular<T> extends User implements RequestsManager, ExperienceStrategy {

    public Regular() {

    }
    public Regular(String fullName) {
        super(fullName);
    }


    public void createRequest(RequestTypes requestType, String description) {
        Request request = new Request(requestType, description, (String) getUsername());
        RequestsHolder.addRequest(request);

    }

    public void deleteRequest(Request request) {
        RequestsHolder.removeRequest(request);
    }

    public void addReview(Production production, int score, String comments) {
        if (getExperience() > 0) {
            Rating rating = new Rating((String) getUsername(), score, comments);
            production.addRating(rating);
            updateExperience(1);
        } else {
            System.out.println("Insufficient experience to add a review.");
        }
    }

    @Override
    public void logout() {
        System.out.println("Regular user logged out.");
    }

    @Override
    public void createRequest(Request r) {
        
    }

    @Override
    public void removeRequest(Request r) {

    }

    @Override
    public int calculateExperience() {
        return 0;
    }

    @Override
    public void update(String notification) {

    }

}
