package org.constants;

import org.example.*;
import java.util.*;
/**
 * This class is used to create the operations
 * it takes an integer, and based on it calls the corresponding function from the functions factory
 *
 *
 */
public class OperationFactory {

    static List<Actor> actors = IMDB.getInstance().getActors();
    static List<Production> productions = IMDB.getInstance().getProductions();
    static List<User<?>> users = IMDB.getInstance().getUsers();
    static List<Request> requests = IMDB.getInstance().getRequests();
    static User<?> currentUser = IMDB.getInstance().getCurrentUser();

    /**
     * This function is used to create the operations
     * @param choice the choice of the user
     */
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
                WriteOutput.printRed("Invalid choice");
                break;
        }


    }

    public static void viewProductionDetails() {
        productions.sort((o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating()));
        for (Production P : productions) {
           WriteOutput.makeBreak();
            P.displayInfo();
            WriteOutput.makeBreak();

        }
    }

    public static void viewActorsDetails() {
        actors.sort(Actor::compareTo);
        for (Actor actor : actors) {
            WriteOutput.makeBreak();
            actor.displayInfo();
            WriteOutput.makeBreak();
        }
    }

    public static void viewNotifications() {
        List<String> notification =  currentUser.getNotifications();
         if (notification.isEmpty()){
             WriteOutput.printGreen("No notifications");
             return;
         }
        for (String s : notification) {
            WriteOutput.printGreen(Arrays.toString(s.split("\n")));
        }
    }

    public static void searchForActorsMoviesSeries() {
        WriteOutput.printBlue("Enter the name of the actor/movie/series you want to search for:");
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
        WriteOutput.printRed(name + " Does not found in the System!!");
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
        Admin admin = new Admin();
       FunctionsFactory.displayUsersName(users);
        String name = FunctionsFactory.readFullName();
        for (User<?> user : users) {
            if (user.getName().equals(name)) {
               admin.removeUser(user);
               admin.removeUserDetails(user);
                WriteOutput.printRed("User removed from the system");
                return;
            }
        }

        // User not found, proceed with adding a new user
        WriteOutput.write(OutPutConstants.accountType);
        int choice = ReadInput.readInteger(1, 3);
        User<?> newUser;
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
            WriteOutput.printGreen("User added to the system");

        } else {
            WriteOutput.printRed("User not added to the system");
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
                    WriteOutput.printBlue("Enter the score:");
                    int score = ReadInput.readInteger(1, 10);
                    String review = ReadInput.readLine("Enter the review:");
                    Rating rating = new Rating(currentUser.getUsername(), score, review);
                    production.addRating(rating);
                    // notify all users who added review to the production that a new review was added
                    for (User<?> user : users) {
                       for (Rating rating1 : production.getRatings()) {
                           if (rating1.getUsername().equals(user.getUsername())){
                              String message = "A new review was added to the production "+production.getTitle()+"\nby "+currentUser.getUsername()+"\n comment "+rating1.getComment()+"\n rated"+rating1.getRating();
                               rating1.addObserver(user);
                               rating1.notifyObservers(message);
                               rating1.removeObserver(user);
                           }
                       }
                    }

                    WriteOutput.printGreen("Review added");
                    UserExperienceContext userExperienceContext = new UserExperienceContext();
                    userExperienceContext.setExperienceStrategy(new AddReviewStrategy());
                    int experience = userExperienceContext.calculateUserExperience();
                    currentUser.updateExperience(experience);
                } else {
                    WriteOutput.printBlue("Enter the review you want to delete:");
                    String review = ReadInput.readLine();
                    for (Rating rating : production.getRatings()) {
                        if (rating.getComment().equals(review) && rating.getUsername().equals(currentUser.getUsername())) {
                            production.getRatings().remove(rating);
                            return;
                        }else {
                            WriteOutput.printRed(currentUser.getUsername()+ " has no this review");
                        }
                    }
                }
                return;
            }
        }
        WriteOutput.printRed("Production not found in the system");


    }

    public static void addDeleteActorMovieSeriesFromSystem() {
        Admin admin = new Admin();
        Contributor contributor = new Contributor();
        WriteOutput.write(OutPutConstants.addDeleteActorOrMovieConstants);
        int choice = ReadInput.readInteger(1, 2);
        WriteOutput.write(OutPutConstants.addDeleteConstant);
        int choice1 = ReadInput.readInteger(1, 2);
        if (choice == 1) {
            if( choice1 == 1) {
                WriteOutput.printBlue("Enter the name of the actor you want to delete:");
                String name = ReadInput.readLine();
                for (Actor actor : actors) {
                    if (actor.getName().equals(name)) {
                        if (currentUser instanceof Admin) {
                            admin.removeActorSystem(name);
                        } else {
                            contributor.removeActorSystem(name);

                        }
                        WriteOutput.printRed("Actor removed from favorites");
                    }
                }
            }else {
             FunctionsFactory.createAndAddActor(actors);

            }
        } else {
            if( choice1 == 1) {
                WriteOutput.printBlue("Enter the name of the production you want to delete:");
                String name = ReadInput.readLine();
                for (Production production : productions) {
                    if (production.getTitle().equals(name)) {
                        productions.remove(production);
                        WriteOutput.printRed("Production removed from favorites");
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
        WriteOutput.printRed("Production not found in the system");


    }


    public static void updateActorDetails() {
        String actorName = ReadInput.readLine("Enter the name of the actor you want to update:");
        for (Actor actor : actors) {
            if (actor.getName().equals(actorName)) {
                FunctionsFactory.updateActor(actor,currentUser);
                return;
            }
        }
        WriteOutput.printRed("Actor not found in the system");


    }

    public static void solveRequests() {
        Admin admin = new Admin();
        Contributor contributor = new Contributor();
        WriteOutput.write(OutPutConstants.solveRequest);
        int choice = ReadInput.readInteger(1, 2);
        FunctionsFactory.displayRequests(requests);
        String name;
        if (choice == 1) {
            name = ReadInput.readLine("Enter the Username of the request you want to solve:");
            for (Request request : requests) {
                if (request.getUsername().equals(name) ) {
                    if (!request.getStatus().equals(RequestStatus.Pending)){
                        WriteOutput.printRed("Request already "+request.getStatus());
                        return;
                    }
                    if (currentUser instanceof Admin) {
                        admin.resolveRequests(request);
                    } else {
                        contributor.resolveRequests(request);
                    }
                    // send notification to the user
                    sendNotification(request);
                }
            }
        } else {
            name = ReadInput.readLine("Enter the username of the request you want to reject:");
            for (Request request : requests) {
                if (request.getUsername().equals(name)) {
                    if (!request.getStatus().equals(RequestStatus.Pending)){
                        WriteOutput.printRed("Request already "+request.getStatus());
                        return;
                    }
                    if (currentUser instanceof Admin) {
                        admin.rejectRequests(request);
                    } else {
                        contributor.rejectRequests(request);
                    }
                    // send notification to the user
                    sendNotification(request);
                    return;
                }
            }
        }
        WriteOutput.printRed("Request not found in the system");

    }

    private static void sendNotification(Request request) {
        for (User<?> user : users) {
            if (user.getUsername().equals(request.getUsername())) {
                String message = ReadInput.readLine("Enter the message you want to send to the user:");
              request.addObserver(user);
                request.notifyObservers(message);
                request.removeObserver(user);
            }
        }
    }

    public static void addRemoveActor(){
        WriteOutput.printBlue("Enter the name:");
            String name = ReadInput.readLine();
            for (Actor actor :actors) {
            if (actor.getName().equals(name)) {
                if (currentUser.getFavoriteActors().contains(actor)) {
                   currentUser.getFavoriteActors().remove(actor);
                    WriteOutput.printRed("Actor removed from favorites");
                } else {
                    currentUser.addToFavoriteActors(actor);
                     WriteOutput.printGreen("Actor added to favorites");
                }
                return;
            }

        }
      WriteOutput.printRed("Actor not found in the system");

}

public static void addRemoveProduction(){
    WriteOutput.printBlue("enter the name production");
        String name = ReadInput.readLine();
            for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                if (currentUser.getFavoriteProductions().contains(production)) {
                   currentUser.getFavoriteProductions().remove(production);
                    WriteOutput.printRed("Production removed from favorites");
                } else {

                    currentUser.addToFavoriteProductions(production);
                    WriteOutput.printGreen("Production added to favorites");
                }
                return;
            }
        }
    WriteOutput.printRed("Production not found in the system");
}
}