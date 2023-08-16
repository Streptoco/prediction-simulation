package engine;

import java.util.Random;

public class RuleActivation {
    private int ticks;
    private double probability;

    public RuleActivation(int ticks, double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public boolean checkActivation(int ticks) {
        Random rand = new Random();
        double newProbability = rand.nextDouble();
        return (this.ticks % ticks == 0) && (newProbability < probability);
    }
}
