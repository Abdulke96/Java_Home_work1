package org.example;


public class AddProductStrategy implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        return 10;
    }
}