package org.constants;

import org.example.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
public class OperationFactory {
    static List<Actor> actors = IMDB.getInstance().getActors();
    static List<Production> productions = IMDB.getInstance().getProductions();
    static List<User<?>> users = IMDB.getInstance().getUsers();
    static List<Request> requests = IMDB.getInstance().getRequests();
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

    public static void viewProductionDetails() {
        productions.sort((o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating()));
        for (Production P : productions) {
            System.out.println("\n======================================\n");
            P.displayInfo();
            System.out.println("\n======================================\n");

        }
    }

    public static void viewActorsDetails() {
        actors.sort(Actor::compareTo);
        for (Actor actor : actors) {
            System.out.println("\n======================================\n");
            actor.displayInfo();
            System.out.println("\n======================================\n");
        }
    }

    public static void viewNotifications() {
        List<String> notification =  currentUser.getNotifications();
         if (notification.isEmpty()){
             System.out.println("No notifications");
             return;
         }
        for (String s : notification) {
            System.out.println(Arrays.toString(s.split("\n")));
        }
    }

    public static void searchForActorsMoviesSeries() {
        System.out.println("Enter the name of the actor/movie/series you want to search for:");
        String name = ReadInput.readLine();
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                actor.displayInfo();
                return;
            }
        }
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                production.displayInfo();
                return;
            }
        }
        System.out.println(name + " Does not found in the System!!");
    }

    public static void addDeleteActorsMoviesSeriesToFavorites() {
     WriteOutput.write(OutPutConstants.actorOrProduction);
       int choice =  ReadInput.readInteger(1,2);
     if (choice==1){
         addRemoveActor();
     }else {
         addRemoveProduction();
     }

    }

    public static void addDeleteUser() {
       FunctionsFactory.displayUsersName(users);
        String name = FunctionsFactory.readFullName();
        for (User<?> user : users) {
            if (user.getName().equals(name)) {
                users.remove(user);
                //delete rating by the user
                   for (Production production : productions) {
                       production.getRatings().removeIf(rating -> rating.getUsername().equals(user.getUsername()));
                   }
                //delete requests by the user
                requests.removeIf(request -> request.getUsername().equals(user.getUsername()));

                System.out.println("User removed from the system");
                return;
            }
        }

        // User not found, proceed with adding a new user
        WriteOutput.write(OutPutConstants.accountType);
        int choice = ReadInput.readInteger(1, 3);
        User<?> newUser;
        Admin<?> admin = new Admin<>();
        if (choice == 1) {
          newUser = admin.addUser(name, AccountType.Admin);
        } else if (choice == 2) {
            newUser = admin.addUser(name, AccountType.Contributor);
        } else {
            newUser = admin.addUser(name, AccountType.Regular);
        }
        if (newUser != null) {
            newUser.setName(name);
            newUser.setUsername(FunctionsFactory.generateUniqueUsername(name));
            users.add(newUser);
            FunctionsFactory.createUserInfoFunction(newUser);
            FunctionsFactory.createUserDetailsFunction(newUser);
            System.out.println("User added to the system");

        } else {
            System.out.println("User not added to the system");
        }
    }



    public static void createDeleteRequest() {
        WriteOutput.write(OutPutConstants.requestConstant);
        int choice = ReadInput.readInteger(1, 2);
        FunctionsFactory.requestCreatorMoreover(requests, choice);
    }

    public static void addDeleteReview() {
      String name = ReadInput.readLine("Enter the name of the production you want to add/delete a review to:");
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                WriteOutput.write(OutPutConstants.reviewConstant);
                int choice = ReadInput.readInteger(1, 2);
                if (choice == 1) {
                    System.out.println("Enter the score:");
                    int score = ReadInput.readInteger(1, 10);
                    String review = ReadInput.readLine("Enter the review:");
                    Rating rating = new Rating(currentUser.getUsername(), score, review);
                    production.addRating(rating);
                    System.out.println("Review added");
                } else {
                    System.out.println("Enter the review you want to delete:");
                    String review = ReadInput.readLine();
                    for (Rating rating : production.getRatings()) {
                        if (rating.getComment().equals(review) && rating.getUsername().equals(currentUser.getUsername())) {
                            production.getRatings().remove(rating);
                            return;
                        }else {
                            System.out.println(currentUser.getUsername()+ " has no this review");
                        }
                    }
                }
                return;
            }
        }
        System.out.println("Production not found in the system");


    }

    public static void addDeleteActorMovieSeriesFromSystem() {
        WriteOutput.write(OutPutConstants.addDeleteActorOrMovieConstants);
        int choice = ReadInput.readInteger(1, 2);
        WriteOutput.write(OutPutConstants.addDeleteConstant);
        int choice1 = ReadInput.readInteger(1, 2);
        if (choice == 1) {
            if( choice1 == 1) {
                System.out.println("Enter the name of the actor you want to delete:");
                String name = ReadInput.readLine();
                for (Actor actor : actors) {
                    if (actor.getName().equals(name)) {
                        actors.remove(actor);

                        System.out.println("Actor removed from favorites");
                    }
                }
            }else {
             FunctionsFactory.createAndActor(actors);

            }
        } else {
            if( choice1 == 1) {
                System.out.println("Enter the name of the production you want to delete:");
                String name = ReadInput.readLine();
                for (Production production : productions) {
                    if (production.getTitle().equals(name)) {
                        productions.remove(production);
                        System.out.println("Production removed from favorites");
                    }
                }
            }else {
                FunctionsFactory.createAndAddProduction(productions);

            }
        }

    }



    public static void updateMovieDetails() {
       String productName = ReadInput.readLine("Enter the name of the production you want to update:");
        for (Production production : productions) {
            if (production.getTitle().equals(productName)) {
              WriteOutput.write(OutPutConstants.chooseMovieSeries);
                if (production instanceof Movie){
                    if ( FunctionsFactory.updateMovieProduction(production,currentUser )) return;
                }else if (production instanceof Series){
                    if ( FunctionsFactory.updateSeriesProduction(production,currentUser )) return;

                }
                return;
            }
        }
        System.out.println("Production not found in the system");


    }


    public static void updateActorDetails() {
        String actorName = ReadInput.readLine("Enter the name of the actor you want to update:");
        for (Actor actor : actors) {
            if (actor.getName().equals(actorName)) {
                FunctionsFactory.updateActor(actor,currentUser);
                return;
            }
        }
        System.out.println("Actor not found in the system");


    }

    public static void solveRequests() {
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
                    currentUser.addToFavoriteActors(actor);
                    System.out.println("Actor added to favorites");
                }
                return;
            }

        }
        System.out.println("Actor not found in the system");

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
                return;
            }
        }
        System.out.println("Production not found in the system");
}
}