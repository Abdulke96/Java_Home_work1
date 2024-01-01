package org.example;

import lombok.Setter;

@Setter
public class UserExperienceContext {
    private ExperienceStrategy experienceStrategy;

    public int calculateUserExperience() {
        if (experienceStrategy != null) {
            return experienceStrategy.calculateExperience();
        }
        return 0;
    }
}

