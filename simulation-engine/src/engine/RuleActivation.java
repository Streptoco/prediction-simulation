package engine;

import java.util.Random;

public class RuleActivation {
    private int ticks = 1;
    private double probability = 1;

    public RuleActivation(int ticks, double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public RuleActivation(int ticks) {
        this.ticks = ticks;
        this.probability = -1;
    }

    public RuleActivation(double probability) {
        this.probability = probability;
        this.ticks = -1;
    }

    public RuleActivation() {
        // default lol
    }

    public boolean checkActivation(int ticks) {
        Random rand = new Random();
        double newProbability = rand.nextDouble();
        return (this.ticks % ticks == 0) && (newProbability < probability);
    }
}
