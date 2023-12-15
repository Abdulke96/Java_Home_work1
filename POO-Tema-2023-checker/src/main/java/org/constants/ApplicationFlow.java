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
        System.out.println("1) View productions details"); // for all
        System.out.println("2) View actors details"); // for all
        System.out.println("3) View notifications"); // for all
        System.out.println("4) Search for actors/movies/series"); // for all
        System.out.println("5) Add/Delete actors/movies/series to/from favorites"); // for all
        System.out.println("6) Add/Delete user");//only for admin
        System.out.println("7)Create/Delete a request"); // for regular and contributor
        System.out.println("8) Add/Delete actor/movie/series/ from system"); // for admin and contributor
        System.out.println("9) Update movie details"); // for admin and contributor
        System.out.println("10) Update actor details"); // for admin and contributor
        System.out.println("11) Solve requests");// for admin and contributor
        System.out.println("12) Logout"); // for all
        System.out.println("Enter your choice:");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if(choice ==1 ){
            System.out.println("favowrite" +currentUser.favorites);
            System.out.println(" exprience"+currentUser.getExperience());
            System.out.println("production conntribution"+currentUser.getProductionsContribution());
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
