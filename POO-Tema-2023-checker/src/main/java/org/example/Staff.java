package org.example;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.constants.FunctionsFactory;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Staff<T extends Comparable<T>> extends User<T> implements StaffInterface {

    private List<Request> assignedRequests;
    public SortedSet<T> contributions;
    private  List<String> productionsContribution;
    private List<String> actorsContribution;
    public Staff(String fullName) {
        super(fullName);
        this.assignedRequests = new ArrayList<>();
        this.contributions = new TreeSet<>();
        this.productionsContribution = new ArrayList<>();
        this.actorsContribution = new ArrayList<>();

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
    public void addContributions(T contribution){
        this.contributions.add(contribution);
    }
    public void removeContributions(T contribution){
        this.contributions.remove(contribution);
    }

    public SortedSet<T> getContributions(){
        //add actors contributions and productions contributions
        contributions.addAll((Collection<? extends T>) productionsContribution);
        contributions.addAll((Collection<? extends T>) actorsContribution);

        return this.contributions;
    }
    public void updateActor(Actor a){
        FunctionsFactory.updateActor(a, this);
    }

    public Iterable<? extends Request> getAssignedRequest() {
        return this.assignedRequests;
    }
}
