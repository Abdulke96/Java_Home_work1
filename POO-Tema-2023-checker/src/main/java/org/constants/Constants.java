package org.constants;

import org.example.Admin;
import org.example.Contributor;
import org.example.User;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public static final String path1 = "POO-Tema-2023-checker/src/main/resources/assets/";
    public static final String path2 = "src/test/resources/testResources/assets/";
    public static final String path = findpath(path1, path2);
    public static String findpath(String path1, String path2){
        try{
            File file = new File(path1 +"imdb1.png");
            if(file.exists()){
                return path1;
            }
            else{
                if (new File(path2+"imdb1.png").exists()){
                    return path2;
                }

            }
        }
        catch(Exception e){
            return path2;
        }
        return path1;
    }
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
   public static int[]  displayOption(User<?> user){
       if(user instanceof Admin){
           for(String action:OutPutConstants.adminActions){
               System.out.println(action);
           }
           return admincode;
       }
       else if(user instanceof Contributor){
           for(String action:OutPutConstants.contributorActions){
               System.out.println(action);
           }
              return contributorcode;
       }
       else{
           for(String action:OutPutConstants.regularActions){
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
    public static List<String> displayOptionsGuide(User<?> user){
        if(user instanceof Admin){
            return OutPutConstants.adminActions;
        }
        else if(user instanceof Contributor){
            return OutPutConstants.contributorActions;
        }
        else{
            return OutPutConstants.regularActions;
        }
    }

    public static List<String> userInfo(User<?> user){
       List<String> userInfo = new ArrayList<>();
          userInfo.add("name: "+user.getName());
          userInfo.add("Email: "+user.getEmail());
          userInfo.add("User Name: "+user.getUsername());
          userInfo.add("Country: "+user.getCountry());
          userInfo.add("Gender: "+user.getGender());
          userInfo.add("Age: "+user.getAge());
          userInfo.add("Experience: "+user.getExperience());
          userInfo.add("Birth Date: "+ user.getBirthDate().format(DateTimeFormatter.ISO_DATE));
        if(user instanceof Admin){
            userInfo.add("UserType: Admin");
        }else if(user instanceof Contributor){
            userInfo.add("UserType: Contributor ");
        }else{
            userInfo.add("UserType: Regular");
        }
        userInfo.add("Product Contribution: ");

         for(String s: user.getProductionsContribution()){
             userInfo.add("          " + s);
         }
        userInfo.add("Favorite Production: ");

        for(String s: user.getFavoriteProductions()){
            userInfo.add("          " + s);
        }
        userInfo.add("Actors Contribution: ");
        for(String s: user.getActorsContribution()){
            userInfo.add("          " + s);
        }
        userInfo.add("Favorite Actors: ");
        for(String s: user.getFavoriteActors()){
            userInfo.add("          " + s);
        }

return userInfo;
    }
}
