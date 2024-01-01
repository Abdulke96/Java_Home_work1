package org.constants;

import org.example.Rating;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *  Class that reads input from the user
 *  It checks the exceptions and returns the input if it is valid
 *  It has functions for reading integers, strings, dates and ratings
 */

public class ReadInput {
    public static int chooseUserMode() {
       WriteOutput.write(OutPutConstants.userModeConstants);
        return readInteger(1,3);
    }
    /**
     *  Function that reads an integer from the user
     *  It checks the exceptions and returns the input if it is valid
     *  @param min the minimum value of the integer
     *  @param max the maximum value of the integer
     *  @return the integer read from the user
     */
    public  static int readInteger(int min, int max){

        int choice;
        try {
            Scanner scanner = new Scanner(System.in);
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            WriteOutput.printRed("Invalid input!");
            return readInteger(min,max);
        }
        if (choice < min || choice > max) {
            WriteOutput.printRed("Invalid input!");
            return readInteger(min,max);
        }
        return choice;

    }

    /**
     * Function that reads a string from the user
        * @return the string read from the user
     */

    public static String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    /**
     *  Function that reads a string from the user
     *  @param message the message that is displayed to the user
     *  @return the string read from the user
     */
    public static String readLine(String message) {
        WriteOutput.printBlue(message);
        return readLine();
    }
    /**
     *  Function that reads a date from the user
     *  @return the date read from the user
     */

    public static LocalDateTime readDate() {
        WriteOutput.printBlue("enter year");
        int year = readInteger(0,2024);
        WriteOutput.printBlue("enter month");
        int month = readInteger(0,12);
        WriteOutput.printBlue("enter day");
        int day = readInteger(0,31);
        return LocalDateTime.of(year,month,day,0,0);

    }
    /**
     *  Function that reads a rating from the user
     *  @return the rating read from the user
     */

    public static Rating readRating() {
        WriteOutput.printBlue("enter username");
        String username = readLine();
        WriteOutput.printBlue("enter rating");
        int rating = readInteger(0,10);
        WriteOutput.printBlue("enter comment");
        String comment = readLine();
        return new Rating(username,rating,comment);
    }
}
