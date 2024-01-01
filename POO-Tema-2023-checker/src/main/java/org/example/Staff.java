package org.example;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.constants.FunctionsFactory;
import org.constants.OperationFactory;
//import org.helper.ContributionsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {

    private List<Request> assignedRequests;
    public SortedSet<T> contributions;
    public Staff(String fullName) {
        super(fullName);
        this.assignedRequests = new ArrayList<>();
        this.contributions = new TreeSet<>();

    }
    public void addProductionSystem(Production p){
        IMDB.getInstance().getProductions().add(p);
    }
    public void addActorSystem(Actor a){
        IMDB.getInstance().getActors().add(a);

    }
    public void removeProductionSystem(String name){
        IMDB.getInstance().getProductions().removeIf(production -> production.getTitle().equals(name));
    }
    public void removeActorSystem(String name){
        IMDB.getInstance().getActors().removeIf(actor -> actor.getName().equals(name));

    }

    public void updateProduction(Production p) {
        if (p instanceof Movie) {
            if (FunctionsFactory.updateMovieProduction(p, this)) return;
        } else if (p instanceof Series) {
            if (FunctionsFactory.updateSeriesProduction(p, this)) return;

        }

    }
    public void updateActor(Actor a){
        FunctionsFactory.updateActor(a, this);
    }

}
