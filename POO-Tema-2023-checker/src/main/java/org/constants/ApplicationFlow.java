package org.constants;

import org.example.*;

import java.util.Scanner;

public  class  ApplicationFlow {
    public static void appFlow() {
        User<?> currentUser = IMDB.getInstance().getCurrentUser();
        System.out.println("Welcome back user " + currentUser.getEmail() + "!");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());
     while (true){
         System.out.println("Choose an Action:");
         int[] code = Constants.displayOption(currentUser);
         System.out.println("Enter your choice:");
         Scanner scanner = new Scanner(System.in);
         int choice = scanner.nextInt();
         while ( !Constants.contains(code,choice)){
             System.out.println("Invalid choice");
             System.out.println("Enter your choice:");
             choice = scanner.nextInt();
         }
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
                 //IMDB.getInstance().run();
                 IMDB.getInstance().setCurrentUser(null);
                 break;
             case 14:
                 System.exit(0);
             default:
                 System.out.println("Invalid choice");
                 break;
         }
         if (choice == 13){
             break;
         }

     }



    }
    public static void viewProductionDetails( ){
        for (Production P : IMDB.getInstance().getProductions()){
            System.out.println("\n======================================\n");
            P.displayInfo();
            System.out.println("\n======================================\n");

        }
    }

    public static void viewActorsDetails(){
        for (Actor actor: IMDB.getInstance().getActors()){
            System.out.println("\n======================================\n");
            actor.displayInfo();
            System.out.println("\n======================================\n");
        }
    }
    public static void viewNotifications(){
        for (String notification: IMDB.getInstance().getCurrentUser().getNotifications()){
            System.out.println("\n======================================\n");
            for (String note: notification.split("\n")){
                System.out.println(note);
            }
            System.out.println("\n======================================\n");
        }
    }

    public static void searchForActorsMoviesSeries(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the actor/movie/series you want to search for:");
        String name = scanner.nextLine();
        for (Actor actor: IMDB.getInstance().getActors()){
            if (actor.getName().equals(name)){
                actor.displayInfo();
            }
        }
        for (Production production: IMDB.getInstance().getProductions()){
            if (production.getTitle().equals(name)){
                production.displayInfo();
            }
        }
    }

    public static void addDeleteActorsMoviesSeriesToFavorites(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the actor/movie/series you want to add/delete to/from favorites:");
        String name = scanner.nextLine();
        for (Actor actor: IMDB.getInstance().getActors()){
            if (actor.getName().equals(name)){
                if (IMDB.getInstance().getCurrentUser().getFavoriteActors().contains(actor)){
                    IMDB.getInstance().getCurrentUser().getFavoriteActors().remove(actor);
                    System.out.println("Actor removed from favorites");
                }
                else{
                    IMDB.getInstance().getCurrentUser().addToFavoriteAtors(actor);
                    System.out.println("Actor added to favorites");
                }
            }
        }
        for (Production production: IMDB.getInstance().getProductions()){
            if (production.getTitle().equals(name)){
                if (IMDB.getInstance().getCurrentUser().getFavoriteProductions().contains(production)){
                    IMDB.getInstance().getCurrentUser().getFavoriteProductions().remove(production);
                    System.out.println("Production removed from favorites");
                }
                else{
                    IMDB.getInstance().getCurrentUser().addToFavoriteProductions(production);
                    System.out.println("Production added to favorites");
                }
            }
        }
    }

    public static void addDeleteUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the user you want to add/delete:");
//        String name = scanner.nextLine().trim();
//        for (User<?> user: IMDB.getInstance().getUsers()){
//            if (user.getUsername().equals(name)){
//                if (IMDB.getInstance().getUsers().contains(user)){
//                    IMDB.getInstance().removeUser(user);
//                    System.out.println("User" + user.getUsername() + "removed");
//                }
//                else{
////                   while (true){
////                       System.out.println("Enter the type of the user you want to add:");
////                       System.out.println("1) Admin");
////                       System.out.println("2) Contributor");
////                       System.out.println("3) Regular");
////                       int choice = scanner.nextInt();
////                       AccountType type = null;
////                       switch (choice){
////                           case 1:
////                               type = AccountType.Admin;
////                               break;
////                           case 2:
////                               type = AccountType.Contributor;
////                               break;
////                           case 3:
////                               type = AccountType.Regular;
////                               break;
////                           default:
////                               System.out.println("Invalid choice");
////                               break;
////                       }
////                          if (type != null){
////
////                            IMDB.getInstance().getUsers().add(Admin.UserFactory.create(name,type));
////                            System.out.println("User" + user.getUsername() + "added");
////                            break;
////                          }
//                }
//
//        }
//            }
    }
      public static void createDeleteRequest(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }
        public static void addDeleteReview(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }

        public static void addDeleteActorMovieSeriesFromSystem(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }

        public static void updateMovieDetails(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }

        public static void updateActorDetails(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }

        public static void solveRequests(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the name of the user you want to add/delete:");


        }


    }


