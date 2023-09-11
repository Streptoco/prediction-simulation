package engine.general.multiThread.api;

import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Rule;
import engine.general.object.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimulationRunner implements Runnable {
    private final World world;
    private Status status;
    private int ticks;
    private int simID;
    private long currentTime;
    private final Date simDate;
    private final SimpleDateFormat simulationDate;

    public SimulationRunner(World world, int id) {
        this.world = world;
        this.status = Status.LOADING;
        this.ticks = 0;
        this.simID = id;
        this.simulationDate = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        this.simDate = new Date();
        this.simulationDate.format(this.simDate);
    }

    public World getWorld() {
        return world;
    }

    public Status getStatus() {
        return status;
    }

    public int getTick() {
        return ticks;
    }

    public long getSimulationRunningTimeInMillis() {
        return System.currentTimeMillis() - simDate.getTime();
    }

    public Map<String, Integer> getAllEntitiesAmount() {
        Map<String, Integer> result = new HashMap<>();
        Map<String, EntityInstanceManager> managers = world.getManagers();
        for (Map.Entry<String, EntityInstanceManager> entry : managers.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getCountInstances());
        }
        return result;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public synchronized void resumeSimulation() {
        this.status = Status.RUNNING;
        System.out.println("\t\t\twhoooooooooooo.... continuing " + Thread.currentThread().getName());
        notifyAll();
    }

    private synchronized void checkIfNeedToPause() {
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
        this.currentTime = System.currentTimeMillis();
        this.status = Status.RUNNING;
        Thread.currentThread().setName("Simulation-Thread-ID-" + this.simID);
        //world.setCurrentTime();this.currentTime = System.currentTimeMillis();
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, this.currentTime) && this.status.equals(Status.RUNNING)) {
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
            checkIfNeedToPause();
            if (this.status.equals(Status.ABORTED)) {
                break;
            }
            ticks++;
        }
        /* TODO:
            1. If simulation is end, find a way to notify the user it ended successfully
            2. Find a way to somehow save all the relevant entites amount and env variable values for re-run of the simulation
         */
    }
}
