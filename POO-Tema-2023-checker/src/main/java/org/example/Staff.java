package org.example;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.helper.ContributionsHandler;

import java.util.List;
import java.util.SortedSet;

public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {
    public List<Request> getAssignedRequests() {
        return assignedRequests;
    }

    public SortedSet<T> getContributions() {
        return contributions;
    }

    private List<Request> assignedRequests;
    @JsonDeserialize(using = ContributionsHandler.class)
    public SortedSet<T> contributions;  // Assuming Object can be Movie or Actor
    // Constructor
//    public Staff(String fullName) {
//        super(fullName);
//        this.assignedRequests = null;
//        this.contributions = null;
//
//        // Initialize assignedRequests and addedItems as needed
//    }
    public void addProductionSystem(Production p){

    }
    public void addActorSystem(Actor a){

    }
    public void removeProductionSystem(String name){

    }
    public void removeActorSystem(String name){

    }
    public void updateProduction(Production p){

    }
    public void updateActor(Actor a){

    }

}
