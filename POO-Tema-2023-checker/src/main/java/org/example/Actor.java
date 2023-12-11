package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Actor implements Comparable<Object> {

    private final String name;
    private final List<Map.Entry<String, String>> performances;  // Name:Type pairs
    private final String biography;

    // Constructor
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

    // Getter methods
    public String getName() {
        return name;
    }

    public List<Map.Entry<String, String>> getPerformances() {
        return performances;
    }

    public String getBiography() {
        return biography;
    }

    // Other methods as needed
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Actor{name='").append(name).append('\'');
        stringBuilder.append(", performances=[");

        for (Map.Entry<String, String> performance : performances) {
            stringBuilder.append("{title='").append(performance.getKey()).append("', type='").append(performance.getValue()).append("'}, ");
        }

        if (!performances.isEmpty()) {
            // Remove the trailing comma and space
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append("]");
        stringBuilder.append(", biography='").append(biography).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
