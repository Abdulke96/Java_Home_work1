package org.example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class User implements Comparable<User> {
    private class Information {
        private Credentials credentials;
        private String name;
        private String gender;
        private int age;
        private LocalDateTime birthDate;

        // Constructor for Information class
        public Information(Credentials credentials, String name, String gender, int age, LocalDateTime birthDate) {
            this.credentials = credentials;
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.birthDate = birthDate;
        }

        // Getter methods for Information class
        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public LocalDateTime getBirthDate() {
            return birthDate;
        }
    }
    private AccountType accountType;
    private final String username;
    private int experience;
    private List<String> notifications;
    private final SortedSet<Object> favorites;  // Assuming Object can be Movie, Series, or Actor

    // Constructor
    public User(String fullName) {
        this.username = generateUniqueUsername(fullName);
        this.experience = 0;
        this.favorites = new TreeSet<>();
        // Other initialization as needed
    }

    // Method to generate a unique username
    private String generateUniqueUsername(String fullName) {
        // Logic to generate a unique username based on the full name
        // You can implement your own logic or use a library for this purpose
        return ""; // Replace with actual implementation
    }

    // Methods for adding/removing favorites
    public void addToFavorites(Object favorite) {
        favorites.add(favorite);
    }

    public void removeFromFavorites(Object favorite) {
        favorites.remove(favorite);
    }

    // Method to update user experience
    public void updateExperience(int points) {
        experience += points;
    }

    // Method to handle user logout
    public abstract void logout();

    // Other methods as needed

    // Comparable interface implementation for sorting users by experience
    @Override
    public int compareTo(User otherUser) {
        return Integer.compare(otherUser.experience, this.experience);
    }

    public Object getUsername() {
        return username;
    }

    public Object getPassword() {
        return "";
    }

    protected Production[] getFavorites() {
        return new Production[0];
    }
}
