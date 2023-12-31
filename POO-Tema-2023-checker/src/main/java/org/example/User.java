package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.*;

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
        private  String name;
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
                this.name = "null";
                this.gender = "null";
                this.age = 0;
                this.birthDate = LocalDateTime.now();
                this.country = "null";

            }
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
    private  List<String> productionsContribution;
    private List<String> actorsContribution;
    private  List<String> favoriteProductions;
    private  List<String> favoriteActors;
    private AccountType userType;
    private String username;
    private Information information;
    private String experience;
    private List<String> notifications;
    public SortedSet<T> favorites ;
    public User(){
        this.username = "null";
        this.experience = "null";
        this.favorites = new TreeSet<>();
        this.information = new Information.InformationBuilder().build();
        this.userType = AccountType.Regular;
        this.notifications = new ArrayList<>();
        this.productionsContribution = new ArrayList<>();
        this.actorsContribution = new ArrayList<>();
        this.favoriteActors = new ArrayList<>();
        this.favoriteProductions = new ArrayList<>();
    }
    public User(String fullName) {

        this.username = generateUniqueUsername(fullName);
        this.experience = "null";
        this.favorites = new TreeSet<>();
        this.information = new Information.InformationBuilder().build();
        this.userType = AccountType.Regular;
        this.notifications = new ArrayList<>();
        this.productionsContribution = new ArrayList<>();
        this.actorsContribution = new ArrayList<>();
        this.favoriteActors = new ArrayList<>();
        this.favoriteProductions = new ArrayList<>();
    }

    private String generateUniqueUsername(String fullName) {
        return " ";
    }
    public void addToFavoriteActors(Actor favorite) {
        favoriteActors.add(favorite.getName());
    }
    public void addToFavoriteProductions(Production favorite) {
      //  T favorites = (T) favorite.getTitle();
        addToFavorites((T) favorite.getTitle());
        favoriteProductions.add(favorite.getTitle());
    }
   public void addToFavorites(T favorite) {
        favorites.add(favorite);
   }



    public void removeFromFavorites(T favorite) {
        favorites.remove(favorite);
    }

    public void updateExperience(int points) {
        experience += points;
    }
    public abstract void logout();

    public String getPassword() {
        return information.getCredentials().getPassword();
    }
    public void setPassword(String password) {
        information.getCredentials().setPassword(password);
    }
    public String getEmail() {
        return information.getCredentials().getEmail();
    }
    public void setEmail(String email) {
        information.getCredentials().setEmail(email);
    }
    @Override
    public void update(String notification) {
        notifications.add(notification);
    }
    protected Production[] getFavorites() {
        return new Production[0];
    }

    // for use

    public String getName(){
        return  getInformation().getName();
    }

    public void setName(String name){
        this.information.setName(name);

    }

    public String getGender(){
        return getInformation().getGender();

    }
    public void setGender(String gender){
        this.information.setGender(gender);
    }
    public String getCountry(){
        return getInformation().getCountry();

    }

    public void setCountry(String country){
        this.information.setCountry(country);
    }
    public int getAge(){
        return getInformation().getAge();

    }
    public void setAge(int age){
        this.information.setAge(age);
    }

    public LocalDateTime getBirthDate(){
        return getInformation().getBirthDate();
    }
    public void setBirthDate(LocalDateTime birthDate){
        this.information.setBirthDate(birthDate);
    }
}
