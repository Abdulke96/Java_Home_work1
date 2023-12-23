package org.example;
import java.util.List;

public class Contributor extends Staff implements RequestsManager , Observer {// Constructor


    public Contributor() {
        super("");
    }
    public Contributor(String fullName) {
        super(fullName);
    }
    public void createRequest(RequestTypes requestType, String description) {

    }


    public void deleteRequest(Request request) {
       RequestsHolder.removeRequest(request);
    }

    public void logout() {
        System.out.println("Contributor logged out.");
    }

    public void resolveRequests() {
        System.out.println("Contributor resolving requests.");
    }

    public void addProduction(Production production) {

        System.out.println("Contributor adding production.");
    }


    public void removeProduction(Production production) {
        System.out.println("Contributor removing production.");
    }

    public void updateProductionInformation(Production production) {
        System.out.println("Contributor updating production information.");
    }

    public void createRequest(Request r) {

    }

    public void removeRequest(Request r) {

    }
    @Override
    public void update(String notification) {

    }
}
