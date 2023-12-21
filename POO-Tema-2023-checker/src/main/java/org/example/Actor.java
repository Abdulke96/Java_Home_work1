package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import lombok.*;

@Data
public class Actor implements Comparable<Actor> {
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
    public int compareTo(@NotNull Actor o) {
        assert this.name != null;
        assert o.name != null;
        return this.name.compareTo((o.name));
    }
    public void displayInfo(){
        System.out.println("Actor: "+ getName());
        System.out.println("Performances: ");
        if (performances!=null)
            for (Map.Entry<String, String> entry : performances) {
            System.out.println("    Title: "+entry.getKey());
            System.out.println("    Type:  "+entry.getValue());
            }
        if(biography != null)
            System.out.println("Biography: " + getBiography());
    }
    //GUI helper functions
public String guiActorString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Actor: ").append(getName()).append("\n");
        stringBuilder.append("Performances: ").append("\n");
        if (performances!=null)
            for (Map.Entry<String, String> entry : performances) {
                stringBuilder.append("    Title: ").append(entry.getKey()).append("\n");
                stringBuilder.append("    Type:  ").append(entry.getValue()).append("\n");
            }
        if(biography != null)
            stringBuilder.append("Biography: ").append(getBiography()).append("\n");
        return stringBuilder.toString();
        }


}
