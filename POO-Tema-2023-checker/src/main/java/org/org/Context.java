package org.org;

public class Context {
    private final Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(){
        strategy.execute();
    }
}

class Main{
    public static void main(String[] args) {
        Context context = new Context(new Add(1,2));
        context.executeStrategy();
        context = new Context(new Sub(1,2));
        context.executeStrategy();
    }

}