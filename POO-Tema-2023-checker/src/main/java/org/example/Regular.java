package org.example;

import java.util.List;

public class Regular extends User implements RequestsManager {

    // Constructor
    public Regular(String fullName) {
        super(fullName);
    }

    // Methods from the RequestsManager interface

    public void createRequest(RequestTypes requestType, String title, String description) {
        Request request = new Request(requestType, title, description, (String) getUsername());
        RequestsHolder.addRequest(request);
        // Additional logic for handling the created request
    }

    public void deleteRequest(Request request) {
        RequestsHolder.removeRequest(request);
        // Additional logic for handling the deleted request
    }

    // Method to add a review (Rating) for a production
    public void addReview(Production production, int score, String comments) {
        if (getExperience() > 0) {
            Rating rating = new Rating((String) getUsername(), score, comments);
            production.addRating(rating);
            updateExperience(1); // Regular user gains 1 point of experience for each review
            // Additional logic for handling the added review
        } else {
            System.out.println("Insufficient experience to add a review.");
        }
    }

    // Override the logout method
    @Override
    public void logout() {
        // Additional logic for regular user logout
        System.out.println("Regular user logged out.");
    }

    @Override
    public void createRequest(Request r) {
        
    }

    @Override
    public void removeRequest(Request r) {

    }

    // Other methods as needed
}
