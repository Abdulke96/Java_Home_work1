package org.example;

import java.util.ArrayList;
import java.util.List;

public class RequestsHolder {
    private static List<Request> requests = new ArrayList<>();

    // Private constructor to prevent instantiation
    private RequestsHolder() {
    }

    // Method to add a request to the list
    public static void addRequest(Request request) {
        requests.add(request);
    }

    // Method to remove a request from the list
    public static void removeRequest(Request request) {
        requests.remove(request);
    }

    // Method to get the list of requests
    public static List<Request> getRequests() {
        return requests;
    }
}

