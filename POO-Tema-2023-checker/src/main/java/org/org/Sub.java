package org.org;

public class Sub implements Strategy {
    private final int number1;
    private final int number2;
 Sub(int number1, int number2){
        this.number1 = number1;
        this.number2 = number2;
    }
    @Override
    public void execute() {
        System.out.println(number1-number2);

    }
}