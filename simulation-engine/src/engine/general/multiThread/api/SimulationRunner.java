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

    private void setStatus(Status status) {
        this.status = status;
    }

    public synchronized void resumeSimulation() {
        if (this.status.equals(Status.PAUSED)) {
            setStatus(Status.RUNNING);
            System.out.println("Continuing " + "[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks);
            notifyAll();
        }
    }

    public void pauseSimulation() {
        setStatus(Status.PAUSED);
    }

    public void abortSimulation() { setStatus(Status.ABORTED);}

    private synchronized void checkIfNeedToPause() {
        if (this.status.equals(Status.PAUSED)) {
            try {
                //while (this.status.equals(Status.PAUSED)) {
                System.out.println("Waiting " + "[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks);
                wait();
                //}
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void manualStopSimulation() {
        if (this.status.equals(Status.RUNNING) || this.status.equals(Status.PAUSED)) {
            setStatus(Status.DONE);
        }
    }


    public void simulationManualStep() {
        if (this.status.equals(Status.PAUSED)) {
            if (world.getTermination().getTermination(ticks, this.currentTime)) {
                System.out.println("[Thread: " + Thread.currentThread().getName() + "] Manual Step Tick number " + ticks);
                if (ticks != 0) {
                    world.getGrid().MoveSacks();

                }

                for (Rule rule : world.getRules()) {
                    if (rule.CheckTicks(ticks)) {
                        synchronized (this.world) {
                            rule.NewInvokeAction(world.getManagers(), world.getEnvironment(), world.getGrid(), ticks);
                        }
                    }
                }
                world.doWhenTickIsOver();
                ticks++;
            }
        }
    }

    @Override
    public void run() {
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Starting the simulation" + " Sim ID: " + simID);
        this.currentTime = System.currentTimeMillis();
        if(!this.status.equals(Status.ABORTED)) {
            this.status = Status.RUNNING;
        }
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, this.currentTime) && !(this.status.equals(Status.ABORTED)) && !(this.status.equals(Status.DONE))) {
            if (ticks != 0) {
                world.getGrid().MoveSacks();
                //System.out.println("[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks);
                //world.getGrid().drawGrid();
            } else {
                //world.getGrid().drawGrid();
            }
            for (Rule rule : world.getRules()) {
                if (rule.CheckTicks(ticks)) {
                    synchronized (this.world) {
                        rule.NewInvokeAction(world.getManagers(), world.getEnvironment(), world.getGrid(), ticks);
                    }
                }
            }
            synchronized (this.world) {
                world.doWhenTickIsOver();
            }
            checkIfNeedToPause();
            if (this.status.equals(Status.ABORTED)) {
                System.out.println("Stopping " + "[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks);
                break;
            }
            ticks++;
        }
        if (!this.status.equals(Status.ABORTED)) {
            this.status = Status.DONE;
        }
        System.out.println("[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks + " Simulation Done with status: " + this.status);
        /* TODO:
            1. If simulation is end, find a way to notify the user it ended successfully
            2. Find a way to somehow save all the relevant entites amount and env variable values for re-run of the simulation
         */
    }
}
