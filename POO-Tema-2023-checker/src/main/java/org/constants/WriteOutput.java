package org.constants;

import java.util.List;

public class WriteOutput {
    public static void write(List<String> output) {
        int i = 0;
        int size = output.size();
        for (i = 0; i < size; i++) {
            if(i == 0){
                System.out.println(output.get(i));
            } else if (i>=1 && i< size-1) {
                System.out.println( i+")" + output.get(i));
            } else {
                System.out.println(output.get(i));
            }
        }
    }
}
