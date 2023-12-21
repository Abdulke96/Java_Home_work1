package org.example;

public class UserExperienceContext {
    private ExperienceStrategy experienceStrategy;

    public void setExperienceStrategy(ExperienceStrategy strategy) {
        this.experienceStrategy = strategy;
    }

    public int calculateUserExperience() {
        if (experienceStrategy != null) {
            return experienceStrategy.calculateExperience();
        }
        return 0;
    }
}

