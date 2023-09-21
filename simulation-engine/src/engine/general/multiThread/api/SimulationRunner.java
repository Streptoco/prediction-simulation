package engine.general.multiThread.api;

import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Rule;
import engine.general.object.World;
import engine.property.api.PropertyInterface;
import enginetoui.dto.basic.impl.WorldDTO;
import simulations.dto.PopulationsDTO;
import simulations.dto.SimulationDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SimulationRunner implements Runnable {
    private final World world;
    private Status status;
    private int ticks;
    private final int simID;
    private long currentTime;
    private final Date simDate;
    private final SimpleDateFormat simulationDate;
    private SimulationDTO simulationDTO;

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

    public void SetVariable(String variableName, int value) {
        if (this.world != null) {
            double from = this.world.getEnvironment().getProperty(variableName).getFrom();
            double to = this.world.getEnvironment().getProperty(variableName).getTo();
            if (value < (int) from || value > (int) to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + (int) from + " to: " + (int) to);
            }
            this.world.getEnvironment().updateProperty(variableName, value);
            this.simulationDTO.addEnvVariable(variableName, value);
        }
    }

    public void SetVariable(String variableName, double value) {
        if (this.world != null) {
            double from = this.world.getEnvironment().getProperty(variableName).getFrom();
            double to = this.world.getEnvironment().getProperty(variableName).getTo();
            if (value < from || value > to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + from + " to: " + to);
            }
            this.world.getEnvironment().updateProperty(variableName, value);
            this.simulationDTO.addEnvVariable(variableName, value);
        }
    }

    private void SetVariableBool(String variableName, String value) {
        if (this.world != null) {
            if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                throw new RuntimeException("The value " + value + " cannot be parsed to boolean\n" +
                        "The value should be \"true\" or \"false\"");
            }
            this.world.getEnvironment().updateProperty(variableName, Boolean.parseBoolean(value));
            this.simulationDTO.addEnvVariable(variableName, Boolean.parseBoolean(value));
        }
    }

    public void SetVariable(String variableName, String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            SetVariableBool(variableName, value);
        } else {
            if (this.world != null) {
                this.world.getEnvironment().updateProperty(variableName, value);
                this.simulationDTO.addEnvVariable(variableName, value);
            }
        }
    }

    public void SetPopulation(String entityName, int population) {
        this.world.createPopulationOfEntity(entityName, population);
        this.simulationDTO.addPopulation(entityName, population);
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

    public void abortSimulation() {
        setStatus(Status.ABORTED);
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

    @Override
    public void run() {
        System.out.println("[Thread: " + Thread.currentThread().getName() + "] Starting the simulation" + " Sim ID: " + simID);
        this.currentTime = System.currentTimeMillis();
        world.initializeEntitiesAmount();
        if (!this.status.equals(Status.ABORTED)) {
            this.status = Status.RUNNING;
        }
        world.assignSacks();
        while (world.getTermination().getTermination(ticks, this.currentTime) && !(this.status.equals(Status.ABORTED)) && !(this.status.equals(Status.DONE))) {
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
