package engine;

import java.sql.Time;

public class Termination {
    private final int ticks;
    private final long howManySecondsToRun;

    public Termination(int ticks, int howManySecondsToRun) {
        this.ticks = ticks;
        this.howManySecondsToRun = howManySecondsToRun * 1000L;
    }

    public boolean getTermination(int currentTick, long outerTimeInMillis) {
        return (currentTick >= this.ticks || outerTimeInMillis >= this.howManySecondsToRun);
    }
}
