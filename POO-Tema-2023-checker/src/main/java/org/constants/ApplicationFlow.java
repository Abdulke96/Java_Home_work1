package org.constants;

import org.example.IMDB;
import org.example.User;

import java.util.Scanner;

public  class  ApplicationFlow {
    public static void contributorFlow() {
        User<?> currentUser = IMDB.getInstance().getCurrentUser();
        System.out.println("Welcome back user " + currentUser.getEmail() + "!");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("User experience: " + currentUser.getExperience());


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
                System.out.println("You chose to add a production");
                break;
            case 2:
                System.out.println("You chose to remove a production");
                break;
            case 3:
                System.out.println("You chose to update a production");
                break;
            case 4:
                System.out.println("You chose to create a request");
                break;
            case 5:
                System.out.println("You chose to remove a request");
                break;
            case 6:
                System.out.println("You chose to resolve requests");
                break;
            case 7:
                System.out.println("You chose to logout");
                break;
                case 8:
                System.out.println("You chose to view productions details");
                break;
            case 9:
                System.out.println("You chose to view actors details");
                break;
            case 10:
                System.out.println("You chose to view notifications");
                break;
            case 11:
                System.out.println("You chose to search for actors/movies/series");
                break;
            case 12:
                System.out.println("You chose to add/delete actors/movies/series to/from favorites");
                break;
            case 13:
                System.out.println("You chose to create/delete a request");
                break;
            case 14:
                System.exit(0);
            default:
                System.out.println("Invalid choice");
                break;
        }


        // Implementation for contributor flow goes here
    }
    public static void adminFlow() {
        System.out.println("Admin flow: Manage system data and requests");
        System.out.println();

    }

    public static void regularFlow() {
        System.out.println("Regular user flow: Explore and interact with the system");


    }
}
