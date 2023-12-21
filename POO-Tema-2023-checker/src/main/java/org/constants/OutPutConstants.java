package org.constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutPutConstants {
 public   static List<String > adminActions = Arrays.asList(
            "1) View productions details",
            "2) View actors details", // for all
            "3) View notifications", // for all
            "4) Search for actors/movies/series", // for all
            "5) Add/Delete actors/movies/series to/from favorites",
            "6) Add/Delete user",
            "9) Add/Delete actor/movie/series/ from system",
            "10) Update movie details", // for admin and contributor
            "11) Update actor details", // for admin and contributor
            "12) Solve requests",// for admin and contributor
            "13) Logout",
            "14) Exit"// for all
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

    public static List<String> addDeleteConstant = Arrays.asList(
            "Choose Operation:",
            "Add",
            "Delete",
            "Your choice: "
    );
    public static List<String> actorOrProduction = Arrays.asList(
            "What you want to Add or delete:",
            "Actor",
            "Production",
            "Your choice: "
    );
}
