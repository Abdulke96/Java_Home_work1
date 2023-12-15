package org.example;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.helper.ContributionsHandler;

import java.util.List;
import java.util.SortedSet;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {

    private List<Request> assignedRequests;
    @JsonDeserialize(using = ContributionsHandler.class)
    public SortedSet<T> contributions;
    public Staff(String fullName) {
        super(fullName);
        this.assignedRequests = null;
        this.contributions = null;

    }
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
