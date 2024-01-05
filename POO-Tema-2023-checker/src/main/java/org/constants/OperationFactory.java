package org.constants;

import org.example.*;
import org.jetbrains.annotations.Nullable;

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
   // static User<?> currentUser = IMDB.getInstance().getCurrentUser();

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

    /**
     * This function is used to view the production details
     */
    public static void viewProductionDetails() {
        productions.sort((o1, o2) -> Double.compare(o2.getAverageRating(), o1.getAverageRating()));
        for (Production P : productions) {
           WriteOutput.makeBreak();
            P.displayInfo();
            WriteOutput.makeBreak();

        }
    }

    /**
     * This function is used to view the actor details
     */
    public static void viewActorsDetails() {
        actors.sort(Actor::compareTo);
        for (Actor actor : actors) {
            WriteOutput.makeBreak();
            actor.displayInfo();
            WriteOutput.makeBreak();
        }
    }

    /**
     * This function is used to view the notifications
     */
    public static void viewNotifications() {
        List<String> notification = IMDB.getInstance().getCurrentUser().getNotifications();
         if (notification.isEmpty()){
             WriteOutput.printGreen("No notifications");
             return;
         }
        for (String s : notification) {
            WriteOutput.printGreen(Arrays.toString(s.split("\n")));
        }
    }

    /**
     * This function is used to search for actors/movies/series
     */
    public static void searchForActorsMoviesSeries() {
        WriteOutput.printBlue("Enter the name of the actor/movie/series you want to search for:");
        String name = ReadInput.readLine();
        // search for the actor
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                actor.displayInfo();
                return;
            }
        }
        // search for the production
        for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                production.displayInfo();
                return;
            }
        }
        WriteOutput.printRed(name + " Does not found in the System!!");
    }

    /**
     * This function is used to add/delete actors/movies/series to favorites
     */
    public static void addDeleteActorsMoviesSeriesToFavorites() {
     WriteOutput.write(OutPutConstants.actorOrProduction);
       int choice =  ReadInput.readInteger(1,2);
     if (choice==1){
         addRemoveActor();
     }else {
         addRemoveProduction();
     }

    }

    /**
     * This function is used to add/delete users
     */
    public static void addDeleteUser() {
        Admin admin = new Admin();
       FunctionsFactory.displayUsersName(users);
        String name = FunctionsFactory.readFullName();
         //delete user
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
            newUser.setPassword(FunctionsFactory.generateRandomPassword());
            newUser.setAddedBy(IMDB.getInstance().getCurrentUser().getUsername());
            while (true) {
                String email = ReadInput.readLine("Enter the email:");
                if (email.contains("@") && email.contains(".")) {
                    newUser.setEmail(email);
                    break;
                } else {
                    WriteOutput.printRed("Invalid email should contain @ and .");
                }
            }
            users.add(newUser);


            FunctionsFactory.createUserInfoFunction(newUser);
            FunctionsFactory.createUserDetailsFunction(newUser);
            // give the user his credentials
            WriteOutput.printGreen("User added to the system");
            WriteOutput.printGreen("Username: " + newUser.getUsername());
            WriteOutput.printGreen("Password: " + newUser.getPassword());
            WriteOutput.printGreen("Email: " + newUser.getEmail());

        } else {
            WriteOutput.printRed("User not added to the system");
        }
    }


    /**
     * This function is used to create/delete requests
     */
    public static void createDeleteRequest() {
        WriteOutput.write(OutPutConstants.requestConstant);
        int choice = ReadInput.readInteger(1, 2);
        FunctionsFactory.requestCreatorMoreover(requests, choice);
    }

    /**
     * This function is used to add/delete reviews
     */
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
                    Rating rating = new Rating(IMDB.getInstance().getCurrentUser().getUsername(), score, review);
                    for (Rating rating1 : production.getRatings()) {
                        if (rating1.getUsername().equals(IMDB.getInstance().getCurrentUser().getUsername())) {
                            WriteOutput.printRed("You already added a review to this production");
                            return;
                        }
                    }
                    production.addRating(rating);
                    // notify all users who added review to the production that a new review was added
                    for (User<?> user : users) {
                       for (Rating rating1 : production.getRatings()) {
                           if (rating1.getUsername().equals(user.getUsername()) && !user.getUsername().equals(IMDB.getInstance().getCurrentUser().getUsername())){
                              String message = "A new review was added to the production "+production.getTitle()+"\nby "+user.getUsername()+"\n comment "+rating1.getComment()+"\n rated "+rating1.getRating();
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
                    IMDB.getInstance().getCurrentUser().updateExperience(experience);
                } else {
                    WriteOutput.printBlue("Enter the review you want to delete:");
                    String review = ReadInput.readLine();
                    for (Rating rating : production.getRatings()) {
                        if (rating.getComment().equals(review) && rating.getUsername().equals(IMDB.getInstance().getCurrentUser().getUsername())) {
                            production.getRatings().remove(rating);
                            return;
                        }else {
                            WriteOutput.printRed(IMDB.getInstance().getCurrentUser().getUsername()+ " has no this review");
                        }
                    }
                }
                return;
            }
        }
        WriteOutput.printRed("Production not found in the system");


    }

    /**
     * This function is used to add/delete actors/movies/series from the system
     */
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
                        if (IMDB.getInstance().getCurrentUser() instanceof Admin) {
                            admin.removeActorSystem(name);
                        } else {
                            contributor.removeActorSystem(name);

                        }
                        WriteOutput.printRed("Actor removed from favorites");
                    }
                }
            }else {
             FunctionsFactory.createAndAddActor();

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


    /**
     * This function is used to update the movie details
     */
    public static void updateMovieDetails() {
       String productName = ReadInput.readLine("Enter the name of the production you want to update:");
        for (Production production : productions) {
            if (production.getTitle().equals(productName)) {
              WriteOutput.write(OutPutConstants.chooseMovieSeries);
                if (production instanceof Movie){
                    if ( FunctionsFactory.updateMovieProduction(production,IMDB.getInstance().getCurrentUser() )) return;
                }else if (production instanceof Series){
                    if ( FunctionsFactory.updateSeriesProduction(production,IMDB.getInstance().getCurrentUser() )) return;

                }
                return;
            }
        }
        WriteOutput.printRed("Production not found in the system");


    }

    /**
     * This function is used to update the actor details
     */
    public static void updateActorDetails() {
        String actorName = ReadInput.readLine("Enter the name of the actor you want to update:");
        for (Actor actor : actors) {
            if (actor.getName().equals(actorName)) {
                FunctionsFactory.updateActor(actor,IMDB.getInstance().getCurrentUser());
                return;
            }
        }
        WriteOutput.printRed("Actor not found in the system");


    }

    /**
     * This function is used to solve the requests
     */
    public static void solveRequests() {
        Admin admin = new Admin();
        Contributor contributor = new Contributor();
        WriteOutput.write(OutPutConstants.solveRequest);
        int choice = ReadInput.readInteger(1, 2);
        FunctionsFactory.displayRequests(requests);
        String name;
        RequestTypes requestTypes;
        if (choice == 1) {
            // user wants to solve the request
            name = ReadInput.readLine("Enter the Username of the request you want to solve:");
            requestTypes = choseRequestTypes();
            for (Request request : requests) {
                if (request.getUsername().equals(name) && request.getType().equals(requestTypes)) {
                    solveTheRequest(request, admin, contributor);
                    return;
                }
            }
        } else {
            // user wants to reject the request
            name = ReadInput.readLine("Enter the username of the request you want to reject:");
            requestTypes = choseRequestTypes();
            for (Request request : requests) {
                if (request.getUsername().equals(name) && request.getType().equals(requestTypes)) {
                    rejectTheRequest(request, admin, contributor);
                 return;
                }
            }
        }
        WriteOutput.printRed("Request not found in the system");

    }

    /**
     * This function is used to choose the request type
     */
    @Nullable
    private static RequestTypes choseRequestTypes() {
        RequestTypes requestTypes;
        WriteOutput.printBlue("Choose the request type:");
        WriteOutput.write(OutPutConstants.requestTypeConstant);
        int choice1 = ReadInput.readInteger(1, 4);
        requestTypes = switch (choice1) {
          case 1 -> RequestTypes.DELETE_ACCOUNT;
          case 2 -> RequestTypes.ACTOR_ISSUE;
          case 3 -> RequestTypes.MOVIE_ISSUE;
          case 4 -> RequestTypes.OTHERS;
          default -> null;};
        return requestTypes;
    }
    /**
     * This function is used to solve the request
     * @param request the request to be solved
     * @param admin the admin who solves the request
     * @param contributor the contributor who solves the request
     */

    private static void solveTheRequest(Request request, Admin admin, Contributor contributor) {
        if (!request.getStatus().equals(RequestStatus.Pending)){
            WriteOutput.printRed("Request already "+request.getStatus());
            return;
        }
        if (IMDB.getInstance().getCurrentUser() instanceof Admin) {
            admin.resolveRequests(request);
        } else {
            contributor.resolveRequests(request);
        }
        // send notification to the user
        sendNotification(request);
    }

    /**
     * This function is used to reject the request
     * @param request the request to be rejected
     * @param admin the admin who rejects the request
     * @param contributor the contributor who rejects the request
     */
    private static void rejectTheRequest(Request request, Admin admin, Contributor contributor) {
        if (!request.getStatus().equals(RequestStatus.Pending)){
            WriteOutput.printRed("Request already "+request.getStatus());
            return;
        }
        if (IMDB.getInstance().getCurrentUser() instanceof Admin) {
            admin.rejectRequests(request);
        } else {
            contributor.rejectRequests(request);
        }
        sendNotification(request);
    }
    /**
     * This function is used to send the notification
     * @param request the request to be sent
     */

    private static void sendNotification(Request request) {
        for (User<?> user : users) {
            if (user.getUsername().equals(request.getUsername())) {
                String message = ReadInput.readLine("Enter the message you want to send to the user:");
                String notification = "Your request was "+request.getStatus()+" by "+IMDB.getInstance().getCurrentUser().getUsername()+"\n"+message;
              request.addObserver(user);
                request.notifyObservers(notification);
                request.removeObserver(user);
            }
        }
    }

    /**
     * This function is used to add/remove an actor
     */
    public static void addRemoveActor(){
        WriteOutput.printBlue("Enter the name:");
            String name = ReadInput.readLine();
            for (Actor actor :actors) {
            if (actor.getName().equals(name)) {
                if (IMDB.getInstance().getCurrentUser().getFavoriteActors().contains(actor)) {
                    IMDB.getInstance().getCurrentUser().getFavoriteActors().remove(actor);
                    WriteOutput.printRed("Actor removed from favorites");
                } else {
                    IMDB.getInstance().getCurrentUser().addToFavoriteActors(actor);
                     WriteOutput.printGreen("Actor added to favorites");
                }
                return;
            }

        }
      WriteOutput.printRed("Actor not found in the system");

}
/**
 * This function is used to add/remove a production
 */
public static void addRemoveProduction(){
    WriteOutput.printBlue("enter the name production");
        String name = ReadInput.readLine();
            for (Production production : productions) {
            if (production.getTitle().equals(name)) {
                if (IMDB.getInstance().getCurrentUser().getFavoriteProductions().contains(production)) {
                    IMDB.getInstance().getCurrentUser().getFavoriteProductions().remove(production);
                    WriteOutput.printRed("Production removed from favorites");
                } else {

                    IMDB.getInstance().getCurrentUser().addToFavoriteProductions(production);
                    WriteOutput.printGreen("Production added to favorites");
                }
                return;
            }
        }
    WriteOutput.printRed("Production not found in the system");
}
}