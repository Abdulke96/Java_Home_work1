package org.constants;

import org.example.Admin;
import org.example.Contributor;
import org.example.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Constants {
    private static final String accountPath1= "src/test/resources/testResources/accounts.json";
    private static final String accountPath2= "POO-Tema-2023-checker/src/main/resources/input/accounts.json";
    private static final String actorsPath1= "src/test/resources/testResources/actors.json";
    private static final String actorsPath2= "POO-Tema-2023-checker/src/main/resources/input/actors.json";

    private static final String productionPath1= "src/test/resources/testResources/production.json";
    private static final String productionPath2= "POO-Tema-2023-checker/src/main/resources/input/production.json";

    private static final String requestsPath1= "src/test/resources/testResources/requests.json";
    private static final String requestsPath2= "POO-Tema-2023-checker/src/main/resources/input/requests.json";
    public static final String account = findFile(accountPath1, accountPath2);
    public static final String actors = findFile(actorsPath1, actorsPath2);
    public static final String production = findFile(productionPath1, productionPath2);
    public static final String requests = findFile(requestsPath1, requestsPath2);

    public static String findFile(String path1, String path2){
        try{
            File file = new File(path1);
            if(file.exists()){
                return path1;
            }
            else{
                return path2;
            }
        }
        catch(Exception e){
            return path2;
        }
    }
    static int[] admincode = {1,2,3,4,5,6,9,10,11,12,13,14};
    static int[] contributorcode = {1,2,3,4,5,7,9,10,11,12,13,14};
    static int[] regularcode = {1,2,3,4,5,7,8,13,14};
    static List<String > adminActions = Arrays.asList(
            "1) View productions details",
            "2) View actors details", // for all
            "3) View notifications", // for all
            "4) Search for actors/movies/series", // for all
            "5) Add/Delete actors/movies/series to/from favorites",
             "6) Add/Delete user",
             "9) Add/Delete actor/movie/series/ from system",
             "10) Update movie details", // for admin and contributor
             "11) Update actor details", // for admin and contributor
             "12) Solve requests"// for admin and contributor
            ,"13) Logout",
            "14) Exit"// for all
    );
    static List<String > contributorActions = Arrays.asList(
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

    static List <String> regularActions = Arrays.asList(
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
   public static int[]  displayOption(User user){
       if(user instanceof Admin){
           for(String action: adminActions){
               System.out.println(action);
           }
           return admincode;
       }
       else if(user instanceof Contributor){
           for(String action: contributorActions){
               System.out.println(action);
           }
              return contributorcode;
       }
       else{
           for(String action: regularActions){
               System.out.println(action);
           }
           return regularcode;
       }


   }

  public static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }
}
