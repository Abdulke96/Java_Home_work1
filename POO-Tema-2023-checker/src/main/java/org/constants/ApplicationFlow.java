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
         int choice = ReadInput.readInteger(1,14);
         while ( !Constants.contains(code,choice)){
             System.out.println("Invalid choice");
             System.out.println("Enter your choice:");
             appFlow();
             return;
         }
        OperationFactory.operation(choice);
         if (choice == 13){
             break;
         }
     }


    }


    }


