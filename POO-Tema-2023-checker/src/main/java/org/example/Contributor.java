package org.example;

import java.util.List;

public class Contributor extends Staff implements RequestsManager {// Constructor


    public Contributor() {
        super("");
    }

    // Methods from the RequestsManager interface

    public void createRequest(RequestTypes requestType, String description) {
        Request request = new Request(requestType ,description, (String) getUsername());
        RequestsHolder.addRequest(request);
        // Additional logic for handling the created request
    }


    public void deleteRequest(Request request) {
        RequestsHolder.removeRequest(request);
        // Additional logic for handling the deleted request
    }

    // Other methods as needed

    // Override logout method
    public void logout() {
        // Additional logic for contributor logout
        System.out.println("Contributor logged out.");
    }

    // Concrete implementation for resolving requests

    public void resolveRequests() {
        // Implementation specific to Contributor for resolving requests
        System.out.println("Contributor resolving requests.");
    }

    // Concrete implementation for adding a production

    public void addProduction(Production production) {
        // Implementation specific to Contributor for adding productions
        System.out.println("Contributor adding production.");
    }

    // Concrete implementation for removing a production

    public void removeProduction(Production production) {
        // Implementation specific to Contributor for removing productions
        System.out.println("Contributor removing production.");
    }

    // Concrete implementation for updating production information
    public void updateProductionInformation(Production production) {
        // Implementation specific to Contributor for updating production information
        System.out.println("Contributor updating production information.");
    }

    public void createRequest(Request r) {

    }

    public void removeRequest(Request r) {

    }

    public void addProductionSystem(Production p) {

    }

    public void addActorSystem(Actor a) {

    }

    public void removeProductionSystem(String name) {

    }

    public void removeActorSystem(String name) {

    }

    public void updateProduction(Production p) {

    }

    public void updateActor(Actor a) {

    }
}
