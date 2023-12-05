package org.example;

import java.util.List;
import java.util.SortedSet;

public abstract class Staff extends User implements StaffInterface {
    private List<Request> assignedRequests;
    private SortedSet<Object> addedItems;  // Assuming Object can be Movie or Actor

    // Constructor
    public Staff(String fullName) {
        super(fullName);
        // Initialize assignedRequests and addedItems as needed
    }

    // Methods from the StaffInterface

    public abstract void resolveRequests();


    public abstract void addProduction(Production production);


    public abstract void removeProduction(Production production);


    public abstract void updateProductionInformation(Production production);

    // Other methods as needed

    // Override logout method

    public void logout() {
        // Additional logic for staff logout
        System.out.println("Staff member logged out.");
    }

    // Additional methods for Staff class
    public void addActor(Actor actor) {
        addedItems.add(actor);
    }

    public void removeActor(Actor actor) {
        addedItems.remove(actor);
    }

    // Other methods as needed
}
