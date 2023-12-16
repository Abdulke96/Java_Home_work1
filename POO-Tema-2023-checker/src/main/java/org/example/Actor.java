package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import lombok.*;

@Data
public class Actor implements Comparable<Object> {
    private final String name;
    private final List<Map.Entry<String, String>> performances;
    private final String biography;
    public Actor() {
        this.name = null;
        this.performances = null;
        this.biography = null;
    }
    public Actor(String name, List<Map.Entry<String, String>> performances, String biography) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        assert this.name != null;
        return this.name.compareTo(((Actor) o).name);
    }
    public void displayInfo(){
        if (getName() != null)
            System.out.println("Actor: " + getName());

       if (getPerformances() != null)
           System.out.println("Performances :\n" + getPerformances());
       if (getBiography() != null)
        System.out.println("biography: " + getBiography());

    }
}
