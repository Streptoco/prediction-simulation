package engine.worldbuilder.factory;

import engine.general.object.Termination;
import engine.worldbuilder.prdobjects.*;

public class TerminationFactory {
    public static Termination BuildTermination(PRDTermination termination) {
        if(termination.getPRDByUser() != null) {
            // TODO: do something to make it stop by user - RONI
            return new Termination(0,0);
        }
        int ticksToTermination = 0;
        int secondsToTermination = 0;
        for (Object object : termination.getPRDBySecondOrPRDByTicks()) {
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
