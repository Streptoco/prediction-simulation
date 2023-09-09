package engine.general.multiThread.api;

import engine.general.object.Rule;
import engine.general.object.World;

public class SimulationRunner implements Runnable {
    private final World world;
    private Status status;

    private int ticks;

    public SimulationRunner(World world) {
        this.world = world;
        this.status = Status.LOADING;
        this.ticks = 0;
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

    public synchronized void resumeSimulation() {
        this.status = Status.RUNNING;
        System.out.println("\t\t\twhoooooooooooo.... continuing " + Thread.currentThread().getName());
        notifyAll();
    }

    private synchronized void checkIfNeedToStop() {
        if (this.status.equals(Status.PAUSED)) {
            try {
                while (this.status.equals(Status.PAUSED)) {
                    System.out.println("\t\t\twhoooooooooooo.... waiting " + Thread.currentThread().getName());
                    wait();
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        if (this.status.equals(Status.ABORTED)) {
            while (true) ;
        }
    }

    public void simulationManualStep() {
        if (this.status.equals(Status.PAUSED)) {
            if (world.getTermination().getTermination(ticks, world.getCurrentTime())) {
                if (ticks != 0) {
                    world.getGrid().MoveSacks();
                    System.out.println("[Manual Step]Thread: " + Thread.currentThread().getName() + "\nMove number " + ticks);
                }
            }
            for (Rule rule : world.getRules()) {
                if (rule.CheckTicks(ticks)) {
                    rule.NewInvokeAction(world.getManagers(), world.getEnvironment(), world.getGrid(), ticks);
                }
            }
            world.doWhenTickIsOver();
            ticks++;
        }
    }


    @Override
    public void run() {
        this.status = Status.RUNNING;
        world.setCurrentTime();
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, world.getCurrentTime())) {
            if (ticks != 0) {
                world.getGrid().MoveSacks();
                System.out.println("Thread: " + Thread.currentThread().getName() + "\nMove number " + ticks);
                //world.getGrid().drawGrid();
            } else {
                //world.getGrid().drawGrid();
            }
            for (Rule rule : world.getRules()) {
                if (rule.CheckTicks(ticks)) {
                    rule.NewInvokeAction(world.getManagers(), world.getEnvironment(), world.getGrid(), ticks);
                }
            }
            world.doWhenTickIsOver();
            checkIfNeedToStop();
            ticks++;
        }
    }
}
