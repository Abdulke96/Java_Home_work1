package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "userType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Regular.class, name = "Regular"),
        @JsonSubTypes.Type(value = Contributor.class, name = "Contributor"),
        @JsonSubTypes.Type(value = Admin.class, name = "Admin")
})
@Data
public abstract class User<T extends Comparable<T>> implements Observer{
    @Data
    private static class Information {
        private Credentials credentials;
        private String name;
        private String gender;
        private int age;
        private LocalDateTime birthDate;
        private String country;
        private Information() {
            InformationBuilder builder = new InformationBuilder();
            this.credentials = builder.credentials;
            this.name = builder.name;
            this.gender = builder.gender;
            this.age = builder.age;
            this.birthDate = builder.birthDate;
            this.country = builder.country;
        }
       private Information(InformationBuilder builder) {
           this.credentials = builder.credentials;
           this.name = builder.name;
           this.gender = builder.gender;
           this.age = builder.age;
           this.birthDate = builder.birthDate;
           this.country = builder.country;
       }
       public static class InformationBuilder {
            private Credentials credentials;
            private String name;
            private String gender;
            private int age;
            private LocalDateTime birthDate;
            private String country;
            public InformationBuilder(){

                this.credentials = new Credentials();
                this.name = null;
                this.gender = null;
                this.age = 0;
                this.birthDate = null;
                this.country = null;

            }
            // Getter methods for Information class
            public InformationBuilder credentials(Credentials credentials) {
                this.credentials = credentials;
                return this;


            }

            public  InformationBuilder name( String name) {
                this.name = name;
                return this;
            }

            public  InformationBuilder gender( String gender) {
                this.gender = gender;
                return this;
            }

            public  InformationBuilder age( int age) {
                this.age = age;
                return this;
            }

            public  InformationBuilder birthDate( LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }
            public  InformationBuilder country( String country) {
                this.country = country;
                return this;
            }

           public Information build() {
                return new Information(this);
           }
       }

    }
    @Getter
    private final List<String> productionsContribution;
    @Getter
    private final List<String> actorsContribution;
    @Getter
    private final List<String> favoriteProductions;

    @Getter
    private final List<String> favoriteActors;
    @Getter
    private AccountType userType;
    private final String username;
    private Information information;
    @Getter
    private int experience;
    @Getter
    private List<String> notifications;
  public SortedSet<T> favorites;  // Assuming Object can be Movie, Series, or Actor


    // Constructor
    public User(){
        this.username =null;
        this.experience = 0;
        this.favorites = new TreeSet<>();// Other initialization as needed
        this.information = new Information.InformationBuilder().build();
        this.userType = null;
        this.notifications = new ArrayList<>();
        this.productionsContribution = new ArrayList<>();
        this.actorsContribution = new ArrayList<>();
        this.favoriteActors = new ArrayList<>();
        this.favoriteProductions = new ArrayList<>();



    }
    public User(String fullName) {
        this.username = generateUniqueUsername(fullName);
        this.experience = 0;
        this.favorites = new TreeSet<>();
        this.information = new Information.InformationBuilder().build();
        this.userType = null;
        this.notifications = new ArrayList<>();
        this.productionsContribution = new ArrayList<>();
        this.actorsContribution = new ArrayList<>();
        this.favoriteActors = new ArrayList<>();
        this.favoriteProductions = new ArrayList<>();



    }

    private String generateUniqueUsername(String fullName) {
        return ""; // Replace with actual implementation
    }
    public void addToFavoriteAtors(Actor favorite) {
        favoriteActors.add(favorite.getName());
    }
    public void addToFavoriteProductions(Production favorite) {
        favoriteProductions.add(favorite.getTitle());
    }




    public void removeFromFavorites(Object favorite) {
        favorites.remove(favorite);
    }

    public void updateExperience(int points) {
        experience += points;
    }
    public abstract void logout();

    public Object getUsername() {
        return username;
    }

    public Object getPassword() {
        return information.getCredentials().getPassword();
    }
    public Object getEmail() {
        return information.getCredentials().getEmail();
    }
    @Override
    public void update(String notification) {
        notifications.add(notification);
    }
    protected Production[] getFavorites() {
        return new Production[0];
    }

    public Information getInformation() {
        return new Information.InformationBuilder().build();
    }

}
