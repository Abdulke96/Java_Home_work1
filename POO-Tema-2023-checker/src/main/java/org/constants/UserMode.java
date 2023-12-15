package org.constants;

import java.util.Scanner;

public class UserMode  {
    public static int chooseUserMode() {
        System.out.println("Please choose user mode:");
        System.out.println("1. CLI user");
        System.out.println("2. GUI user");
        System.out.println("3. Exit");
        System.out.print("Your choice: ");
        int choice = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return chooseUserMode();
        }
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid input!");
            return chooseUserMode();
        }
        return choice;
    }
}
