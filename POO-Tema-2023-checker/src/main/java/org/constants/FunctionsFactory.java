package org.constants;

import org.example.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;
import static org.constants.OperationFactory.currentUser;
public class FunctionsFactory {
    public static List<Integer> storeUserDetails(int max, List<String> options) {
        List<Integer> userDetailsOperations = new ArrayList<>();
        WriteOutput.write(options);
        int choice = 0;
        while (choice != max) {
            choice = ReadInput.readInteger(1, max);
            userDetailsOperations.add(choice);
            System.out.println("your choice is  " + options.get(choice));

        }
        return userDetailsOperations;

    }

    static void createUserInfoFunction(User<?> newUser) {
        List<Integer> infoOperations = FunctionsFactory.storeUserDetails(7, OutPutConstants.addUserInformationsDetailConstants);
        for (Integer operations : infoOperations) {
            switch (operations) {
                case 1:
                    while (true) {
                        String email = ReadInput.readLine("Enter the email:");
                        if (email.contains("@") && email.contains(".")) {
                            newUser.setEmail(email);
                            break;
                        } else {
                            System.out.println("Invalid email should contain @ and .");
                        }
                    }

                    break;
                case 2:
                    while (true) {
                        String password = ReadInput.readLine("Enter the password:");
                        if (password.length() > 4) {
                            newUser.setPassword(password);
                            break;
                        } else {
                            System.out.println("password must be at least 4 characters");
                        }
                    }

                    break;
                case 3:
                    System.out.println("Enter the age:");
                    newUser.setAge(ReadInput.readInteger(0, 100));
                    break;
                case 4:
                    WriteOutput.write(OutPutConstants.genderConstants);
                    int genderChoice = ReadInput.readInteger(1, 3);
                    newUser.setGender(OutPutConstants.genderConstants.get(genderChoice));
                    break;
                case 5:
                    System.out.println("Enter the birth date:");
                    LocalDateTime birthDate = ReadInput.readDate();
                    newUser.setBirthDate(birthDate);
                    break;
                case 6:
                    String country = ReadInput.readLine("Enter the country:");
                    newUser.setCountry(country);
                    break;
                case 7:

                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public static void createUserDetailsFunction(User<?> newUser) {
        List<Integer> operations = FunctionsFactory.storeUserDetails(7, OutPutConstants.addUserDetailConstants);
        for (Integer operation : operations) {
            switch (operation) {
                case 1:
                    System.out.println("Enter the experience:");
                    int experience = ReadInput.readInteger(0, 100);
                    newUser.setExperience(String.valueOf(experience));
                    break;
                case 2:
                    List<String> productionsContribution = new ArrayList<>();
                    System.out.println("Enter the number of productionsContribution:");
                    int numberProductionsContribution = ReadInput.readInteger(1, 100);
                   for (int i = 1; i <= numberProductionsContribution; i++) {
                        productionsContribution.add(ReadInput.readLine("Enter the productionsContribution:"));
                    }
                    newUser.setProductionsContribution(productionsContribution);


                    break;
                case 3:
                    List<String> actorsContribution = new ArrayList<>();
                    System.out.println("Enter the number of actorsContribution:");
                    int numberOfActorsContribution = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfActorsContribution; i++) {
                        actorsContribution.add(ReadInput.readLine("Enter the actorsContribution:"));
                    }
                    newUser.setActorsContribution(actorsContribution);

                    break;
                case 4:
                    List<String> favoriteProductions = new ArrayList<>();
                    System.out.println("Enter the number of favoriteProductions:");
                    int numberOfFavoriteProductions = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfFavoriteProductions; i++) {
                        favoriteProductions.add(ReadInput.readLine("Enter the favoriteProductions:"));
                    }
                    newUser.setFavoriteProductions(favoriteProductions);
                    break;
                case 5:
                    List<String> favoriteActors = new ArrayList<>();
                    System.out.println("Enter the number of favoriteActors:");
                    int numberOfFavoriteActors = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfFavoriteActors; i++) {
                        favoriteActors.add(ReadInput.readLine("Enter the favoriteActors:"));
                    }
                    newUser.setFavoriteActors(favoriteActors);
                    break;
                case 6:
                    System.out.println("notifications");
                    String notification = ReadInput.readLine();
                    newUser.setEmail(notification);
                    break;
                case 7:

                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    static void createAndActor(List<Actor> actors) {
        List<Integer> options = FunctionsFactory.storeUserDetails(4, OutPutConstants.addActorDetailConstants);
        Actor actor = new Actor();
        List<Map.Entry<String, String>> performances = new ArrayList<>();
        for (Integer option : options) {
            switch (option) {
                case 1:
                    String name = ReadInput.readLine("enter the name of the actor you want to add");
                    actor.setName(name);
                    break;
                case 2:
                    String biography = ReadInput.readLine("enter the biography");
                    actor.setBiography(biography);
                    break;
                case 3:
                    System.out.println("add performances");
                    System.out.println("enter the number of performances");
                    int numberOfPerformances = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfPerformances; i++) {
                        String title = ReadInput.readLine("enter the title of the performance");
                        String role = ReadInput.readLine("enter the role of the performance");
                        performances.add(new AbstractMap.SimpleEntry<>(title, role));
                    }
                    actor.setPerformances(performances);
                    break;
                case 4:
                    if (actor.getName() != null) {
                        actors.add(actor);
                    }
                    break;
                default:
                    System.out.println("invalid choice enter number between 1 and 4");
                    break;
            }
        }


    }

    public static void createAndAddProduction(List<Production> productions) {

        WriteOutput.write(OutPutConstants.chooseMovieSeries);
        int choice = ReadInput.readInteger(1, 2);
        if (choice == 1) {
            Series series = new Series();
            List<Integer> options = FunctionsFactory.storeUserDetails(9, OutPutConstants.seriesAddProductionDetailConstants);
            createSeries(productions, options, series);
        } else {
            Movie movie = new Movie();
            List<Integer> options = FunctionsFactory.storeUserDetails(9, OutPutConstants.movieAddProductionDetailConstants);
            createMovie(productions, options, movie);
        }
    }

    private static void createMovie(List<Production> productions, List<Integer> options, Movie movie) {
        movie.setType("Movie");
        for (Integer option : options) {
            switch (option) {
                case 1:
                    String title = ReadInput.readLine("enter the title of the movie you want to add");
                    movie.setTitle(title);
                    break;
                case 2:
                    List<String> directors = new ArrayList<>();
                    System.out.println("enter number of directors");
                    int numberOfDirectors = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfDirectors; i++) {
                        directors.add(ReadInput.readLine("enter the directors of the movie you want to add"));
                    }
                    movie.setDirectors(directors);
                    break;
                case 3:
                    List<String> actors = new ArrayList<>();
                    System.out.println("enter number of actors");
                    int numberOfActors = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfActors; i++) {
                        actors.add(ReadInput.readLine("enter the actors of the movie you want to add"));
                    }
                    movie.setActors(actors);
                    break;
                case 4:
                    List<Rating> ratings = new ArrayList<>();
                    System.out.println("enter number of ratings you want to add");
                    int numberOfRatings = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfRatings; i++){
                        System.out.println("enter rating number "+i+" of the movie you want to add");
                        ratings.add(ReadInput.readRating());
                    }
                    movie.setRatings(ratings);
                    break;
                case 5:
                    List<Genre> genres = new ArrayList<>();
                     int i = 1;
                    for (Genre genre: Genre.values()) {
                        System.out.println((i++)+")"+genre);
                    }
                    System.out.println("enter the genres of the movie you want to add ");
                    System.out.println("enter the number of genres");
                    int numberOfGenres = ReadInput.readInteger(1, 100);
                    for (int j = 1; j <= numberOfGenres; j++) {
                        int genreChoice = ReadInput.readInteger(1, 23);
                        genres.add(Genre.values()[genreChoice - 1]);
                    }
                    movie.setGenres(genres);

                    break;
                case 6:
                    String plot = ReadInput.readLine("enter the plot of the movie you want to add");
                    movie.setPlot(plot);
                    break;
                case 7:
                    System.out.println("enter the duration of the movie you want to add");
                    int duration = ReadInput.readInteger(0, 1000);
                    movie.setDuration(duration + " minutes");
                    break;
                case 8:
                    System.out.println("enter the release year of the movie you want to add");
                    movie.setReleaseYear(ReadInput.readInteger(1000, 2024));
                    break;
                case 9:
                    if (movie.getTitle() != null) {
                        productions.add(movie);
                    }
                    break;
                default:
                    System.out.println("invalid choice enter number between 1 and 11");
                    break;
            }
        }
        movie.calculateAverageRating() ;
    }

    private static void createSeries(List<Production> productions, List<Integer> options, Series series) {
        series.setType("Series");

        for (Integer option : options) {
            switch (option) {
                case 1:
                    String title = ReadInput.readLine("enter the title of the series you want to add");
                    series.setTitle(title);
                    break;
                case 2:
                    List<String> directors = new ArrayList<>();
                    System.out.println("enter number of directors");
                    int numberOfDirectors = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfDirectors; i++) {
                        directors.add(ReadInput.readLine("enter the directors of the series you want to add"));
                    }
                    series.setDirectors(directors);
                    break;
                case 3:
                    List<String> actors = new ArrayList<>();
                    System.out.println("enter number of actors");
                    int numberOfActors = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfActors; i++) {
                        actors.add(ReadInput.readLine("enter the actors of the series you want to add"));
                    }
                    series.setActors(actors);
                    break;
                case 4:
                    List<Rating> ratings = new ArrayList<>();
                    System.out.println("enter number of ratings you want to add");
                    int numberOfRatings = ReadInput.readInteger(1, 100);
                    for (int i = 1; i <= numberOfRatings; i++){
                        System.out.println("enter rating number "+i+" of the series you want to add");
                        ratings.add(ReadInput.readRating());
                    }
                     series.setRatings(ratings);
                    break;
                case 5:
                    List<Genre> genres = new ArrayList<>();
                    int i = 1;
                    for (Genre genre: Genre.values()) {
                        System.out.println((i++)+")"+genre);
                    }
                    System.out.println("enter the genres of the movie you want to add ");
                    System.out.println("enter the number of genres");
                    int numberOfGenres = ReadInput.readInteger(1, 100);
                    for (int j = 1; j <= numberOfGenres; j++) {
                        int genreChoice = ReadInput.readInteger(1, 23);
                        genres.add(Genre.values()[genreChoice - 1]);
                    }
                    series.setGenres(genres);
                    break;
                case 6:
                    String plot = ReadInput.readLine("enter the plot of the series you want to add");
                    series.setPlot(plot);
                    break;
                case 7:
                    System.out.println("enter the release year of the series you want to add");
                    series.setReleaseYear(ReadInput.readInteger(1000, 2024));
                    break;
                case 8:
                    System.out.println("enter the number of seasons of the series you want to add");
                    series.setNumSeasons(ReadInput.readInteger(1, 100));
                    createAndAddSeason(series);
                    break;
                case 9:
                    if (series.getTitle() != null) {
                        productions.add(series);
                    }
                    break;
                default:
                    System.out.println("invalid choice enter number between 1 and 11");
                    break;
            }
        }
        series.calculateAverageRating() ;
    }

    public static List<Episode> createAndAddEpisode() {
       Episode episode = new Episode();
       List<Episode> episodes = new ArrayList<>();
        System.out.println("enter the number of episodes ");
        int numberOfEpisodes = ReadInput.readInteger(1, 100);
        for (int j = 1; j <= numberOfEpisodes; j++) {
           String title = ReadInput.readLine("enter the title of the episode you want to add");
           System.out.println("enter the duration of the episode you want to add");
           int duration = ReadInput.readInteger(0, 1000);
           episode.setDuration(duration + " minutes");
           episode.setEpisodeName(title);
              episodes.add(episode);
        }
        return episodes;
    }

    public static void createAndAddSeason(Series series ) {
        Map<String, List<Episode>> seasons = new HashMap<>();
        int seasonNumber = series.getNumSeasons();
        for (int i = 1; i <= seasonNumber; i++) {
            System.out.println("enter the episodes of season " + i);
            String seasonName = "season " + i + ":";
            List<Episode> episodes = createAndAddEpisode();
            seasons.put(seasonName, episodes);
        }
        series.setSeasons(seasons);
    }
  public static boolean updateMovieProduction(Production production, User<?> currentUser) {
        System.out.println("You are ready to update Series:");
        List<Integer> update = FunctionsFactory.storeUserDetails(7,OutPutConstants.updateMovieDetails);
        for (Integer integer : update) {
            switch (integer){
                case 1:
                    System.out.println("Enter the new title:");
                    String title = ReadInput.readLine();
                    production.setTitle(title);
                    break;
                case 2:
                    System.out.println("Enter the new type:");
                    String type = ReadInput.readLine();
                    production.setType(type);
                    break;
                case 3:
                    System.out.println("Enter the old directors:");
                    String oldDirector = ReadInput.readLine();
                    System.out.println("Enter the new directors:");
                    String director = ReadInput.readLine();
                    production.getDirectors().remove(oldDirector);
                    production.getDirectors().add(director);
                    break;
                case 4:
                    System.out.println("Enter the old actors:");
                    String oldActor = ReadInput.readLine();
                    System.out.println("Enter the new actors:");
                    String actor = ReadInput.readLine();
                    production.getActors().remove(oldActor);
                    production.getActors().add(actor);

                    break;
                case 5:
                    System.out.println("Enter the old genres:");
                    String oldGenre = ReadInput.readLine();
                    System.out.println("Enter the new genres:");
                    String genre = ReadInput.readLine();
                    //check if the old genre is in the Genre enum
                    for (Genre value : Genre.values()) {
                        if (value.name().equals(oldGenre)) {
                            production.getGenres().remove(value);
                            production.getGenres().add(Genre.valueOf(genre));

                        }
                    }


                    break;
                case 6:
                    System.out.println("Enter old rating:");
                    int oldRating = ReadInput.readInteger(1,10);
                    System.out.println("Enter new rating:");
                    int rating = ReadInput.readInteger(1,10);
                    System.out.println("Enter the review you want to update:");
                    String review = ReadInput.readLine();
                    if (editRating(production, currentUser, oldRating, rating, review)) return true;
                    break;
                case 7:
                    System.out.println("Enter the new plot:");
                    String plot = ReadInput.readLine();
                    production.setPlot(plot);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
        return false;
    }

    public static boolean updateSeriesProduction(Production production, User<?> currentUser) {
        System.out.println("You are ready to update Series:");
        List<Integer> update = FunctionsFactory.storeUserDetails(7,OutPutConstants.updateSeriesDetails);
        for (Integer integer : update) {
            switch (integer) {
                case 1:
                    System.out.println("Enter the new title:");
                    String title = ReadInput.readLine();
                    production.setTitle(title);
                    break;
                case 2:
                    System.out.println("Enter the new type:");
                    String type = ReadInput.readLine();
                    production.setType(type);
                    break;
                case 3:
                    System.out.println("Enter the old directors:");
                    String oldDirector = ReadInput.readLine();
                    System.out.println("Enter the new directors:");
                    String director = ReadInput.readLine();
                    production.getDirectors().remove(oldDirector);
                    production.getDirectors().add(director);
                    break;
                case 4:
                    System.out.println("Enter the old actors:");
                    String oldActor = ReadInput.readLine();
                    System.out.println("Enter the new actors:");
                    String actor = ReadInput.readLine();
                    production.getActors().remove(oldActor);
                    production.getActors().add(actor);

                    break;
                case 5:
                    System.out.println("Enter the old genres:");
                    String oldGenre = ReadInput.readLine();
                    System.out.println("Enter the new genres:");
                    String genre = ReadInput.readLine();
                    for (Genre value : Genre.values()) {
                        if (value.name().equals(oldGenre)) {
                            production.getGenres().remove(value);
                            production.getGenres().add(Genre.valueOf(genre));

                        }
                    }
                case 6:
                    System.out.println("Enter old rating:");
                    int oldRating = ReadInput.readInteger(1, 10);
                    System.out.println("Enter new rating:");
                    int rating = ReadInput.readInteger(1, 10);
                    System.out.println("Enter the review you want to update:");
                    String review = ReadInput.readLine();
                    if (editRating(production, currentUser, oldRating, rating, review)) return true;
                    break;
                case 7:
                    System.out.println("Enter the new plot:");
                    String plot = ReadInput.readLine();
                    production.setPlot(plot);
                    break;
            }}
        return false;
    }

    private static boolean editRating(Production production, User<?> currentUser, int oldRating, int rating, String review) {
        for (Rating rating1 : production.getRatings()) {
            if (rating1.getComment().equals(review) && rating1.getValue() == oldRating) {
                production.getRatings().remove(rating1);
                Rating rating2 = new Rating(currentUser.getUsername(), rating, review);
                production.getRatings().add(rating2);
                return true;
            }
        }
        return false;
    }

    public static void updateActor(Actor actor, User<?> currentUser) {
        System.out.println("You are ready to update Actor:");
        List<Integer> update = FunctionsFactory.storeUserDetails(4,OutPutConstants.updateActorDetails);
        for (Integer integer : update) {
            switch (integer) {
                case 1:
                    System.out.println("Enter the new name:");
                    String name = ReadInput.readLine();
                    actor.setName(name);
                    break;
                case 2:
                    System.out.println("Enter the new biography:");
                    String biography = ReadInput.readLine();
                    actor.setBiography(biography);
                    break;
                case 3:
                    System.out.println("Enter the old performances:");
                    String oldPerformance = ReadInput.readLine();
                    System.out.println("Enter the new performances:");
                    String performance = ReadInput.readLine();
                    for (Map.Entry<String, String> entry : actor.getPerformances()) {
                        if (entry.getKey().equals(oldPerformance)) {
                            actor.getPerformances().remove(entry);
                            actor.getPerformances().add(new AbstractMap.SimpleEntry<>(performance, entry.getValue()));
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
public static void requestCreatorMoreover(List<Request> requests, int choice) {
        if (choice == 1){
            System.out.println("Enter the description of the request:");
            String description = ReadInput.readLine();
            WriteOutput.write(OutPutConstants.requestTypeConstant);
            int requestType = ReadInput.readInteger(1,4);
            String to = null;
            switch (requestType){
                case 1:
                    Request request = new Request(RequestTypes.DELETE_ACCOUNT,description,currentUser.getUsername());
                    requests.add(request);
                    to = ReadInput.readLine("Enter the name of the admin you want to send the request to:");
                    break;
                case 2:
                    Request request1 = new Request(RequestTypes.ACTOR_ISSUE,description, currentUser.getUsername());
                    requests.add(request1);
                    to = ReadInput.readLine("Enter the name of the contributor/admin you want to send the request to:");
                    break;
                case 3:
                    Request request2 = new Request(RequestTypes.MOVIE_ISSUE,description, currentUser.getUsername());
                    requests.add(request2);
                    to = ReadInput.readLine("Enter the name of the contributor/admin you want to send the request to:");
                    break;
                case 4:
                    Request request3 = new Request(RequestTypes.OTHERS,description, currentUser.getUsername());
                    requests.add(request3);
                    to = ReadInput.readLine("Enter the name of the admin you want to send the request to:");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            requests.get(requests.size()-1).setTo(to);
            System.out.println("Request created");
        } else {
            String currentDescription = ReadInput.readLine("Enter the name of the request you want to delete:");
            for (Request request : requests) {
                if (request.getDescription().equals(currentDescription) && request.getUsername().equals(currentUser.getUsername())) {
                    requests.remove(request);
                    System.out.println("Request deleted");
                    return;
                }
            }
            System.out.println("Request not found");
        }
    }


    public static String generateUniqueUsername(String fullName) {
        String[] names = fullName.split(" ");
        String baseUsername = names[0].toLowerCase() + "." + names[1].toLowerCase();
        String username = baseUsername;
        int i = 1;
        List<User<?>> users = IMDB.getInstance().getUsers();

        // Check if the username already exists in the list
        while (userExists(users, username)) {
            username = baseUsername + i;
            i++;
        }
        return username;
    }

    private static boolean userExists(List<User<?>> users, String username) {
        for (User<?> user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    @NotNull
    public static String readFullName() {
        System.out.println("Enter the name of the user you want to add/delete:");
        String name = ReadInput.readLine();
        if(!name.contains(" ")){
            while (!name.contains(" ")){
                System.out.println("Invalid name, please enter the full name:");
                name = ReadInput.readLine();
            }
        }
        return name;
    }
    static void displayUsersName(List<User<?>> users) {
        System.out.println("do you wanna see users details 1 yes 2 no");
        int choice = ReadInput.readInteger(1,2);
        if (choice == 1){
            System.out.println("\n======================================\n");
            for (User<?> user : users) {
                System.out.println(user.getName());
            }
            System.out.println("\n======================================\n");
        }
    }
}
