package engine;

import java.util.Random;

public class RuleActivation {
    private int ticks = 1;
    private double probability = 1;
    private boolean alwaysActivate;
    public RuleActivation(int ticks, double probability) {
        this.ticks = ticks;
        this.probability = probability;
        this.alwaysActivate = false;
    }

    public RuleActivation(int ticks) {
        this.ticks = ticks;
        this.probability = 1;
        this.alwaysActivate = false;
    }

    public RuleActivation(double probability) {
        this.probability = probability;
        this.ticks = 1;
        this.alwaysActivate = false;
    }

    public RuleActivation() {
        this.alwaysActivate = true;
    }

    public boolean checkActivation(int ticks) {
        Random rand = new Random();
        double newProbability = rand.nextDouble();
        if (alwaysActivate) {
            return true;
        }
        else if (ticks != 0) {
            return (ticks % this.ticks == 0) && (newProbability < probability);
        }
        else {
            return false;
        }
    }
}
