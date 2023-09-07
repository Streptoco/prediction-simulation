package engine.general.multiThread.api;

import engine.general.object.Rule;
import engine.general.object.World;
import engine.grid.impl.Grid;

public class SimulationRunner implements Runnable {
    private final World world;
    private Status status;

    public SimulationRunner(World world) {
        this.world = world;
        this.status = Status.LOADING;
    }

    public World getWorld() {
        return world;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void run() {
        this.status = Status.RUNNING;
        int ticks = 0;
        world.setCurrentTime();
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, world.getCurrentTime())) {
            if (ticks != 0) {
                world.getGrid().MoveSacks();
                System.out.println("Move number " + ticks);
                world.getGrid().drawGrid();
            } else {
                world.getGrid().drawGrid();
            }
            for (Rule rule : world.getRules()) {
                if (rule.CheckTicks(ticks)) {
                    rule.NewInvokeAction(world.getManagers(), world.getEnvironment(), world.getGrid(), ticks);
                }
            }
            world.doWhenTickIsOver();
            if(ticks == 10) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ticks++;
        }
    }
}
