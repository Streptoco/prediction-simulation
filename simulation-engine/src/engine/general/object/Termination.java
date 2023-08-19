package engine.general.object;

import java.sql.Time;

public class Termination {
    private final int ticks;
    private final long howManySecondsToRun;

    public Termination(int ticks, int howManySecondsToRun) {
        this.ticks = ticks;
        this.howManySecondsToRun = howManySecondsToRun * 1000L;
    }

    public boolean getTermination(int currentTick, long outerTimeInMillis) {
        long currentTime = System.currentTimeMillis();
        currentTime -= outerTimeInMillis;
        return (currentTick <= this.ticks && currentTime <= this.howManySecondsToRun);
    }
}
