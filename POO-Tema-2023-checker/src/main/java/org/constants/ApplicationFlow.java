package org.constants;

import org.example.*;

/**
 * This class is used to welcome the user and display the options he can choose from in cli
 */
public  class  ApplicationFlow {
    public static void appFlow() {
        User<?> currentUser = IMDB.getInstance().getCurrentUser();
        WriteOutput.printGreen("Welcome back user " + currentUser.getEmail() + "!");
        WriteOutput.printGreen("Username : "+currentUser.getUsername());
        WriteOutput.printGreen("Email: "+currentUser.getEmail());
        WriteOutput.printGreen("Experience: "+currentUser.getExperience());
     while (true){
         WriteOutput.printBlue("Choose an Action:");
         int[] code = Constants.displayOption(currentUser);
         WriteOutput.printBlue("Enter your choice:");
         int choice = ReadInput.readInteger(1,14);
         while ( !Constants.contains(code,choice)){
             WriteOutput.printRed("Invalid choice");
             WriteOutput.printBlue("Enter your choice:");
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


