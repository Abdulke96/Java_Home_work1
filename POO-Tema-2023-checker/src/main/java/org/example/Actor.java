package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.*;

@Data
public class Actor implements Comparable<Actor> {
    private String name;
    @Setter(AccessLevel.PUBLIC)
    private  List<Map.Entry<String, String>> performances;
    private  String biography;
    private String addedBy;
    public Actor() {
        this.name = "null";
        this.performances = new ArrayList<>();
        this.biography = null;
        this.addedBy = "null";
    }
    public Actor(String name, List<Map.Entry<String, String>> performances, String biography) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
        this.addedBy = "null";
    }
    public int getNumberOfPerformances(){
        return performances.size();
    }
    public void addPerformance(String title, String type){
        performances.add(new AbstractMap.SimpleEntry<>(title, type));
    }
    public void setKey(String key, String newKey){
        for (Map.Entry<String, String> entry : performances) {
            if(entry.getKey().equals(key)){
                performances.set(performances.indexOf(entry), new AbstractMap.SimpleEntry<>(newKey, entry.getValue()));
            }
        }
    }
    public void setValue(String key, String newValue){
        for (Map.Entry<String, String> entry : performances) {
            if(entry.getKey().equals(key)){
                entry.setValue(newValue);
            }
        }
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
public String guiDisplay(){
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
