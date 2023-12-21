package org.constants;

import org.example.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class OperationFactory {
    static List<Actor> actors = IMDB.getInstance().getActors();
    static List<Production> productions = IMDB.getInstance().getProductions();
    static User<?> currentUser = IMDB.getInstance().getCurrentUser();
    public static void operation(int choice) {
        switch (choice) {

            case 1:
                viewProductionDetails();
                break;
            case 2:
                viewActorsDetails();
                break;
            case 3:
                viewNotifications();
                break;
            case 4:
                searchForActorsMoviesSeries();
                break;
            case 5:
                addDeleteActorsMoviesSeriesToFavorites();
                break;
            case 6:
                addDeleteUser();
                break;
            case 7:
                createDeleteRequest();
                break;
            case 8:
                addDeleteReview();
                break;
            case 9:
                addDeleteActorMovieSeriesFromSystem();
                break;
            case 10:
                updateMovieDetails();
                break;
            case 11:
                updateActorDetails();
                break;
            case 12:
                solveRequests();
                break;
            case 13:
                IMDB.getInstance().setCurrentUser(null);
                break;
            case 14:
                System.exit(0);
            default:
                System.out.println("Invalid choice");
                break;
        }


    }

    public static void viewProductionDetails(/*id 1*/) {
        //productions.sort(Production::compareTo);
        productions.sort((o1, o2) -> {
            return Double.compare(o2.getAverageRating(), o1.getAverageRating());
        });
        for (Production P : productions) {
            System.out.println("\n======================================\n");
            P.displayInfo();
            System.out.println("\n======================================\n");

        }
    }

    public static void viewActorsDetails(/*id 2*/) {
        actors.sort(Actor::compareTo);
        for (Actor actor : actors) {
            System.out.println("\n======================================\n");
            actor.displayInfo();
            System.out.println("\n======================================\n");
        }
    }

    public static void viewNotifications(/*id 3*/) {
        List<String> notification =  currentUser.getNotifications();
         if (notification.isEmpty()){
             System.out.println("No notifications");
             return;
         }
        for (String s : notification) {
            System.out.println(Arrays.toString(s.split("\n")));
        }
    }

    public static void searchForActorsMoviesSeries(/*id 4*/) {
        System.out.println("Enter the name of the actor/movie/series you want to search for:");
        String name = ReadInput.readLine();
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                actor.displayInfo();
            }
        }
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                production.displayInfo();
            }
            return;
        }
        System.out.println(name + "Does not exist");
    }

    public static void addDeleteActorsMoviesSeriesToFavorites(/*id 5*/) {
     WriteOutput.write(OutPutConstants.actorOrProduction);
       int choice =  ReadInput.readInteger(1,2);
     if (choice==1){
         addRemoveActor();
     }else {
         addRemoveProduction();
     }



    }

   public static void   addDeleteUser(/*id 6*/){
         System.out.println("Enter the name of the user you want to add/delete:");
         String name = ReadInput.readLine();
         for (Actor actor : actors ) {
              if (actor.getName().equals(name)) {
                if (currentUser.getFavoriteActors().contains(actor)) {
                     currentUser.getFavoriteActors().remove(actor);
                     System.out.println("Actor removed from favorites");
                } else {
                     IMDB.getInstance().getCurrentUser().addToFavoriteActors(actor);
                     System.out.println("Actor added to favorites");
                }
              }
         }
         for (Production production : productions) {
              if (production.getTitle().equals(name)) {
                if (currentUser.getFavoriteProductions().contains(production)) {
                     currentUser.getFavoriteProductions().remove(production);
                     System.out.println("Production removed from favorites");
                } else {
                     IMDB.getInstance().getCurrentUser().addToFavoriteProductions(production);
                     System.out.println("Production added to favorites");
                }
              }
         }

   }

    public static void createDeleteRequest(/*id 9*/) {
        WriteOutput.write(OutPutConstants.requestConstant);
        int choice = ReadInput.readInteger(1, 2);
        if (choice == 1){
            System.out.println("Enter the description of the request:");
            String description = ReadInput.readLine();
            System.out.println("enter request type:");
            System.out.println( "DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS");
            String requestType = ReadInput.readLine();
            if (requestType.equals("DELETE_ACCOUNT") || requestType.equals("ACTOR_ISSUE") || requestType.equals("MOVIE_ISSUE") || requestType.equals("OTHERS")){
               RequestTypes requestTypes = RequestTypes.valueOf(requestType);
                Request request = new Request(requestTypes,description,IMDB.getInstance().getCurrentUser().getUsername().toString());
                IMDB.getInstance().getRequests().add(request);
                System.out.println("Request created");
            }else {
                System.out.println("Invalid request type");
                createDeleteRequest();
                return;
            }
        }

    }

    public static void addDeleteReview(/*id 10*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");


    }

    public static void addDeleteActorMovieSeriesFromSystem(/*id 11*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");


    }

    public static void updateMovieDetails(/*id 12*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");


    }

    public static void updateActorDetails(/*id 14*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");


    }

    public static void solveRequests(/*id 15*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");


    }
public static void addRemoveActor(){
    System.out.println("Enter the name:");
            String name = ReadInput.readLine();
            for (Actor actor :actors) {
            if (actor.getName().equals(name)) {
                if (currentUser.getFavoriteActors().contains(actor)) {
                   currentUser.getFavoriteActors().remove(actor);
                    System.out.println("Actor removed from favorites");
                } else {
                    IMDB.getInstance().getCurrentUser().addToFavoriteActors(actor);
                    System.out.println("Actor added to favorites");
                }
            }
        }

}

public static void addRemoveProduction(){
    System.out.println("enter the name production");
        String name = ReadInput.readLine();
            for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                if (currentUser.getFavoriteProductions().contains(production)) {
                   currentUser.getFavoriteProductions().remove(production);
                    System.out.println("Production removed from favorites");
                } else {

                    currentUser.addToFavoriteProductions(production);
                    System.out.println("Production added to favorites");
                }
            }
        }
}
}