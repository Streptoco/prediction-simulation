package engine.worldbuilder.factory;

import engine.Termination;
import engine.worldbuilder.prdobjects.PRDBySecond;
import engine.worldbuilder.prdobjects.PRDByTicks;
import engine.worldbuilder.prdobjects.PRDTermination;

public class TerminationFactory {
    public static Termination BuildTermination(PRDTermination termination) {
        int ticksToTermination = 0;
        int secondsToTermination = 0;
        for (Object object : termination.getPRDByTicksOrPRDBySecond()) {
            if (object.getClass().equals(PRDBySecond.class)) {
                PRDBySecond prdBySecond = (PRDBySecond) object;
                secondsToTermination = prdBySecond.getCount();
            }
            else if (object.getClass().equals(PRDByTicks.class)) {
                PRDByTicks prdByTicks = (PRDByTicks) object;
                ticksToTermination = prdByTicks.getCount();
            }
        }
        return new Termination(ticksToTermination,secondsToTermination);
    }
}
