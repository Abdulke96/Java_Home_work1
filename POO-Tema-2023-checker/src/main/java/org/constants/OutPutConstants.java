package org.constants;

import java.util.Arrays;
import java.util.List;

public class OutPutConstants {

 public   static List<String > adminActions = Arrays.asList(
            "1) View productions details",
            "2) View actors details",
            "3) View notifications",
            "4) Search for actors/movies/series",
            "5) Add/Delete actors/movies/series to/from favorites",
            "6) Add/Delete user",
            "9) Add/Delete actor/movie/series/ from system",
            "10) Update movie details",
            "11) Update actor details",
            "12) Solve requests",
            "13) Logout",
            "14) Exit"
    );
  public  static List<String > contributorActions = Arrays.asList(
            "1) View productions details",
            "2) View actors details", // for all
            "3) View notifications", // for all
            "4) Search for actors/movies/series", // for all
            "5) Add/Delete actors/movies/series to/from favorites",
            "7)Create/Delete a request", // for regular and contributor
            "9) Add/Delete actor/movie/series/ from system", // for admin and contributor
            "10) Update movie details", // for admin and contributor
            "11) Update actor details", // for admin and contributor
            "12) Solve requests",// for admin and contributor
            "13) Logout",
            "14) Exit"

    );

  public  static List <String> regularActions = Arrays.asList(
            "1) View productions details",
            "2) View actors details", // for all
            "3) View notifications", // for all
            "4) Search for actors/movies/series", // for all
            "5) Add/Delete actors/movies/series to/from favorites",
            "7)Create/Delete a request",
            "8) Add/Delete a review for a product.",
            "13) Logout",
            "14) Exit"
    );
    public static List<String> userModeConstants = Arrays.asList(
            "Please choose user mode:",
            "CLI user",
            "GUI user",
            "Exit",
            "Your choice: "
    );

    public static List<String> requestConstant = Arrays.asList(
            "Choose Operation:",
            "Create Request",
            "Delete Request",
            "Your choice: "
    );
    public static List<String> requestTypeConstant = Arrays.asList(
            "Choose Request Type:",
            "DELETE_ACCOUNT",
            "ACTOR_ISSUE",
            "MOVIE_ISSUE",
            "OTHERS",
            "Your choice: "
    );
    public static List<String> addDeleteActorOrMovieConstants = Arrays.asList(
            "Choose Operation:",
            "Add or Delete Actor from system",
            "Add or Delete Movie/Series from system",
            "Your choice: "
    );
    public static List<String> addDeleteConstant = Arrays.asList(
            "Choose Operation:",
            "Delete",
            "Add",
            "Your choice: "
    );
    public static List<String> actorOrProduction = Arrays.asList(
            "What you want to Add or delete:",
            "Actor",
            "Production",
            "Your choice: "
    );

    public static List<String> genderConstants = Arrays.asList(
            "What is your Gender?:",
            "female",
            "male",
            "neutral",
            "Your choice: "
    );
    public static List<String> accountType = Arrays.asList(
            "Which account you want to create:",
            "Admin",
            "Contributor",
            "Regular ",
            "Your choice: "
    );

    public static List<String> addUserDetailConstants = Arrays.asList(
            "choose the details you want to add for the user and type done when you are done:",
            "experience",
            "productionsContribution",
            "actorsContribution ",
            "favoriteProductions",
            "favoriteActors",
            "notifications",
            "done",
            "Your choices: "
    );

    public static List<String> addUserInformationsDetailConstants = Arrays.asList(
            " chose the information's detail you want to add and chose done when  you are done:",
            "email",
            "password",
            "age",
            "gender",
            "birthDate",
            "country",
            "done",
            "Your choices: "
    );
    public static List<String> addActorDetailConstants = Arrays.asList(
            "choose the details you want to add for the actor and type done when you are done:",
            "name",
            "biography",
            "performances",
            "done",
            "Your choices: "
    );

    public static List<String> movieAddProductionDetailConstants = Arrays.asList(
            "choose the details you want to add for the production and type done when you are done:",
            "title",
            "directors",
            "actors",
            "ratings",
            "genre",
            "plot",
            "duration",
            "releaseYear",
            "done",
            "Your choices: "
    );
    public static List<String> seriesAddProductionDetailConstants = Arrays.asList(
            "choose the details you want to add for the production and type done when you are done:",
            "title",
            "directors",
            "actors",
            "ratings",
            "genre",
            "plot",
            "releaseYear",
            "numSeasons",
            "done",
            "Your choices: "
    );
    public static List<String> chooseMovieSeries = Arrays.asList(
            "Choose Operation:",
            "Series",
            "Movie",
            "Your choice: "
    );
    public static List<String> reviewConstant = Arrays.asList(
            "Choose Operation:",
            "Add Review",
            "Delete Review",
            "Your choice: "
    );
  public static List<String> updateMovieDetails = Arrays.asList(
            "Choose Operation:",
            "Update Title",
            "Update Directors",
            "Update Actors",
            "Update Ratings",
            "Update Genre",
            "Update Plot",
            "Update Duration",
            "Update Release Year",
            "Your choice: "

  );
    public static List<String> updateSeriesDetails = Arrays.asList(
                "Choose Operation:",
                "Update Title",
                "Update Directors",
                "Update Actors",
                "Update Ratings",
                "Update Genre",
                "Update Plot",
                "Update Release Year",
                "Update Number of Seasons",
                "Your choice: "
  );


  public static List<String> updateActorDetails = Arrays.asList(
            "Choose Operation:",
            "Update Name",
            "Update Biography",
            "Update Performances",
            "Your choice: "
  );
}
