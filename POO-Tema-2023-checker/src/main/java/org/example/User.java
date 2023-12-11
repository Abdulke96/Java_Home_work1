package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public abstract class User<T extends Comparable<T>>{
    private static class Information {
        private Credentials credentials;
        private String name;
        private String gender;
        private int age;
        private LocalDateTime birthDate;
        private String country;
       private Information(InformationBuilder builder) {
           this.credentials = builder.credentials;
           this.name = builder.name;
           this.gender = builder.gender;
           this.age = builder.age;
           this.birthDate = builder.birthDate;
           this.country = builder.country;
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
                this.birthDate = LocalDateTime.now();
                this.country = "default";

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

           public User.Information build() {
                return new User.Information(this);
           }
       }

    }

    public List<String> getProductionsContribution() {
        return productionsContribution;
    }

    public List<String> getActorsContribution() {
        return actorsContribution;
    }

    //********************
    private final List<String> productionsContribution;
    private final List<String> actorsContribution;
    private final List<String> favoriteProductions;

    public List<String> getFavoriteProductions() {
        return favoriteProductions;
    }

    public List<String> getFavoriteActors() {
        return favoriteActors;
    }

    private final List<String> favoriteActors;
    //*********************
    private AccountType userType;
    private final String username;
    private Information information;
    private int experience;
    private List<String> notifications;
  public SortedSet<T> favorites;  // Assuming Object can be Movie, Series, or Actor
    public AccountType getUserType() {
        return userType;
    }

    public List<String> getNotifications() {
        return notifications;
    }


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

    // Method to generate a unique username
    private String generateUniqueUsername(String fullName) {
        return ""; // Replace with actual implementation
    }

    // Methods for adding/removing favorites
    public void addToFavorites(Object favorite) {
        favorites.add((T) favorite);
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

    protected Production[] getFavorites() {
        return new Production[0];
    }
    public int getExperience() {
        return experience;
    }
    public Information getInformation() {
        return new Information.InformationBuilder().build();
    }

}
