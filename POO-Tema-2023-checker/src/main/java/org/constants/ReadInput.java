package org.constants;

import java.util.Scanner;

public class ReadInput {
    public static int chooseUserMode() {
       WriteOutput.write(OutPutConstants.userModeConstants);
        return readInteger(1,3);
    }
    public  static int readInteger(int min, int max){
        int choice = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return readInteger(min,max);
        }
        if (choice < min || choice > max) {
            System.out.println("Invalid input!");
            return readInteger(min,max);
        }
        return choice;

    }

    public static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
