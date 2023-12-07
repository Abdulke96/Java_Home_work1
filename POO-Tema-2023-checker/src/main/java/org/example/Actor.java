package org.example;

import java.util.List;
import java.util.Map;

public class Actor {

    private final String actorName;
    private final List<Map.Entry<String, String>> roles;  // Name:Type pairs
    private final String biography;

    // Constructor
    public Actor(String actorName, List<Map.Entry<String, String>> roles, String biography) {
        this.actorName = actorName;
        this.roles = roles;
        this.biography = biography;
    }

    // Getter methods
    public String getActorName() {
        return actorName;
    }

    public List<Map.Entry<String, String>> getRoles() {
        return roles;
    }

    public String getBiography() {
        return biography;
    }

    // Other methods as needed
}
