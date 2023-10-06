package engine.general.multiThread.api;

import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Rule;
import engine.general.object.Termination;
import engine.general.object.World;
import engine.property.api.PropertyInterface;
import simulation.dto.PopulationsDTO;
import simulation.dto.SimulationDTO;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SimulationRunner implements Runnable {
    private final World world;
    private Termination termination;
    private Status status;
    private int ticks;
    private final int simID;
    private long currentTime;
    private final Date simDate;
    private final SimpleDateFormat simulationDate;
    private SimulationDTO simulationDTO;
    private long runningTime = 0;

    public SimulationRunner(World world, int id) {
        this.world = world;
        this.status = Status.LOADING;
        this.ticks = 0;
        this.simID = id;
        this.simulationDate = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        this.simDate = new Date();
        this.simulationDate.format(this.simDate);
        this.simulationDTO = new SimulationDTO();
    }

    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    public World getWorld() {
        return world;
    }

    public Status getStatus() {
        return status;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public int getTick() {
        return ticks;
    }

    public long getSimulationRunningTimeInMillis() {
        return this.runningTime;
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
        if (this.status.equals(Status.RUNNING)) {
            setStatus(Status.PAUSED);
        }
    }

    public synchronized void abortSimulation() {
        if (this.status.equals(Status.RUNNING)) {
            setStatus(Status.ABORTED);
        } else if (this.status.equals(Status.PAUSED)) {
            setStatus(Status.ABORTED);
            notifyAll();
        }
    }

    private synchronized void checkIfNeedToPause() {
        if (this.status.equals(Status.PAUSED)) {
            try {
                System.out.println("Waiting " + "[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks);
                wait();
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
                world.doWhenTickIsOver(this.ticks);
                ticks++;
            }
        }
    }

    public SimulationDTO getSimulationDTO() {
        for(Map.Entry<String,EntityInstanceManager> entry : world.getManagers().entrySet()) {
            this.simulationDTO.addPopulation(entry.getKey(), entry.getValue().getCountInstances());
        }
        for(PropertyInterface property : world.getEnvironment().GetAllEnvVariables()) {
            this.simulationDTO.addEnvVariable(property.getName(), property.getValue());
        }
        return this.simulationDTO;
    }

    public Map<Integer, PopulationsDTO> getEntitiesAmountPerTick() {
        return world.getEntitiesAmountPerTick();
    }

    public double getConsistency(String entityName, String propertyName) {
        if (status.equals(Status.DONE) || status.equals(Status.ABORTED)) {
            return world.getConsistency(entityName, propertyName);
        } else {
            throw new RuntimeException("The simulation: " + simID + " is still running, so can't get consistency now");
        }
    }

    public Map<String, Integer> GetHistogram(String entityName, String propertyName) {
        Map<String, Integer> resultMap = new HashMap<>();
        EntityInstanceManager entity = this.world.getManagers().get(entityName);
        for (EntityInstance currentInstance : entity.getInstances()) {
            if (!currentInstance.isAlive()) {
                continue;
            }
            PropertyInterface currentProperty = currentInstance.getPropertyByName(propertyName);
            if (resultMap.containsKey((currentProperty.getValue().toString()))) {
                int val = (resultMap.get(currentProperty.getValue().toString())) + 1;
                resultMap.put((currentInstance.getPropertyByName(propertyName).getValue().toString()), val);
            } else {
                resultMap.put(currentProperty.getValue().toString(), 1);
            }
        }
        return resultMap;
    }

    public double averageValueOfProperty(String entityName, String propertyName) {
        return this.world.averageValueOfProperty(entityName, propertyName);
    }


    @Override
    public void run() {
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Starting the simulation" + " Sim ID: " + simID);
        this.currentTime = System.currentTimeMillis();
        this.world.initializeEntitiesAmount();
        if (!this.status.equals(Status.ABORTED)) {
            this.status = Status.RUNNING;
        }
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, this.runningTime) && !(this.status.equals(Status.ABORTED)) && !(this.status.equals(Status.DONE))) {
            LocalDateTime currenTime = LocalDateTime.now();
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
            synchronized (this.world) {
                world.doWhenTickIsOver(this.ticks);
            }
            this.runningTime += Duration.between(currenTime, LocalDateTime.now()).toMillis();
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
        System.out.println("[Thread: " + Thread.currentThread().getName() + "]" + " Sim ID: " + simID + " Tick: " + ticks + " Simulation Done in " + this.getSimulationRunningTimeInMillis() / 1000 + " seconds with status: " + this.status);
    }
}
