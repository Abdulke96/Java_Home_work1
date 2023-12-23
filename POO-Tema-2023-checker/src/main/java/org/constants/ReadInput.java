package org.constants;

import org.example.Rating;

import java.time.LocalDateTime;
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
    public static String readLine(String message) {
        System.out.println(message);
        return readLine();
    }

    public static LocalDateTime readDate() {
        System.out.println("enter year");
        int year = readInteger(0,2024);
        System.out.println("enter month");
        int month = readInteger(0,12);
        System.out.println("enter day");
        int day = readInteger(0,31);
        return LocalDateTime.of(year,month,day,0,0);

    }

    public static Rating readRating() {
        System.out.println("enter username");
        String username = readLine();
        System.out.println("enter rating");
        int rating = readInteger(0,10);
        System.out.println("enter comment");
        String comment = readLine();
        return new Rating(username,rating,comment);
    }
}
