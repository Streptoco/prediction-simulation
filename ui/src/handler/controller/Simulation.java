package handler.controller;

import engine.general.multiThread.api.Status;
import javafx.beans.property.*;

public class Simulation {

    private IntegerProperty simulationID;
    private LongProperty seconds;
    private IntegerProperty ticks;
    private StringProperty status;
    private DoubleProperty progress;

    public Simulation() {
        simulationID = new SimpleIntegerProperty();
        seconds = new SimpleLongProperty();
        ticks = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
        progress = new SimpleDoubleProperty();
    }

    public double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getSimulationID() {
        return simulationID.get();
    }

    public IntegerProperty simulationIDProperty() {
        return simulationID;
    }

    public void setSimulationID(int simulationID) {
        this.simulationID.set(simulationID);
    }

    public long getSeconds() {
        return seconds.get();
    }

    public LongProperty secondsProperty() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds.set(seconds);
    }

    public int getTicks() {
        return ticks.get();
    }

    public IntegerProperty ticksProperty() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks.set(ticks);
    }
}
