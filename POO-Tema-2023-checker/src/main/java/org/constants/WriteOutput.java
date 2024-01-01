package org.constants;

import java.util.List;

/**
 *This class is used to write the output
 * it takes a list of strings and prints them to the console
 *
 */
public class WriteOutput {
    public static void write(List<String> output) {
        int i;
        int size = output.size();
        for (i = 0; i < size; i++) {
            if(i == 0){
                WriteOutput.printGreen(output.get(i));
            } else if (i>=1 && i< size-1) {
                WriteOutput.printGreen( i+")" + output.get(i));
            } else {
                WriteOutput.printGreen(output.get(i));
            }
        }
    }
    public static void makeBreak(){
       for (int i = 0; i < 30; i++) {
          System.out.print("⬤");
           System.out.print("\u001B[32m"+"⬤"+"\u001B[0m");
              System.out.print("\u001B[31m"+"⬤"+"\u001B[0m");
       }
        System.out.println();
    }
    public static void printGreen(String message){
        System.out.println("\u001B[32m"+ message +"\u001B[0m");
    }
    public static void printRed(String message){
        System.out.println("\u001B[31m"+ message +"\u001B[0m");
    }
    public static void printBlue(String message){
        System.out.println("\u001B[34m"+ message +"\u001B[0m");
    }
}
