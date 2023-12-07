package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.helper.UserDeserializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Regular.class, name = "Regular"),
        @JsonSubTypes.Type(value = Contributor.class, name = "Contributor"),
        @JsonSubTypes.Type(value = Admin.class, name = "Admin")
})
public abstract class User implements Comparable<User> {
    private static class Information {
        private Credentials credentials;
        private String name;
        private String gender;
        private int age;
        private LocalDateTime birthDate;
        private String country;
        public Information(){
            this.credentials = new Credentials();
            this.name = null;
            this.gender = null;
            this.age = 0;
            this.birthDate = LocalDateTime.now();
            this.country = "default";

        }

        // Constructor for Information class
        public  Information(Credentials credentials, String name, String gender, int age, LocalDateTime birthDate) {
            this.credentials = credentials;
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.birthDate = birthDate;
            this.country = "default";
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
        public String getCountry(){
            return country;
        }
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    ", age=" + age +
                    ", birthDate=" + birthDate +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    private AccountType accountType;
    private final String username;
    private Information information;
    private int experience;
    private List<String> notifications;
    private final SortedSet<Object> favorites;  // Assuming Object can be Movie, Series, or Actor
    private final List<String> productionsContribution;
    private final List<String> actorsContribution;
    private final List<String> favoriteProductions;
    private final List<String> favoriteActors;

    public AccountType getAccountType() {
        return accountType;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public List<String> getProductionsContribution() {
        return productionsContribution;
    }

    public List<String> getActorsContribution() {
        return actorsContribution;
    }

    public List<String> getFavoriteProductions() {
        return favoriteProductions;
    }

    public List<String> getFavoriteActors() {
        return favoriteActors;
    }



    // Constructor
    public User(){
        this.username =null;
        this.experience = 0;
        this.favorites = new TreeSet<>();// Other initialization as needed
        this.information = new Information();
        this.accountType = null;
        this.notifications = null;
        this.productionsContribution = null;
        this.actorsContribution = null;
        this.favoriteProductions = null;
        this.favoriteActors = null;
        


    }
    public User(String fullName) {
        this.username = generateUniqueUsername(fullName);
        this.experience = 0;
        this.favorites = new TreeSet<>();// Other initialization as needed
        this.information = new Information();
        this.accountType = null;
        this.notifications = null;
        this.productionsContribution = null;
        this.actorsContribution = null;
        this.favoriteProductions = null;
        this.favoriteActors = null;

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
    public int getExperience() {
        return experience;
    }
    public Information getInformation() {
        return new Information();
    }

}
