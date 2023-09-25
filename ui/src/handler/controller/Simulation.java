package handler.controller;

import engine.entity.impl.EntityDefinition;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;

public class Simulation {

    private IntegerProperty simulationID;
    private LongProperty seconds;
    private IntegerProperty ticks;
    private StringProperty status;
    private DoubleProperty progress;
    private DoubleProperty tickProgress;
    private IntegerProperty entityAmount;

    private BooleanProperty isSimulationDone;
    private ListProperty<EntityDefinition> entityList;
    private ScatterChart<Number, Number> entitiesAmountScatter;

    // this is used for getting a list of <EntityDefinition> from the DTO.

    public Simulation() {
        simulationID = new SimpleIntegerProperty();
        seconds = new SimpleLongProperty();
        ticks = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
        progress = new SimpleDoubleProperty();
        tickProgress = new SimpleDoubleProperty();
        entityAmount = new SimpleIntegerProperty();
        isSimulationDone = new SimpleBooleanProperty();
        entityList = new SimpleListProperty<>();
        entitiesAmountScatter = null;
    }

    public ObservableList<EntityDefinition> getEntityList() {
        return entityList.get();
    }

    public ListProperty<EntityDefinition> entityListProperty() {
        return entityList;
    }

    public void setEntityList(ObservableList<EntityDefinition> entityList) {
        this.entityList.set(entityList);
    }

    public void setEntityAmount(int entityAmount) {
        this.entityAmount.set(entityAmount);
    }

    public double getTickProgress() {
        return tickProgress.get();
    }

    public DoubleProperty tickProgressProperty() {
        return tickProgress;
    }

    public void setTickProgress(double tickProgress) {
        this.tickProgress.set(tickProgress);
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

    public BooleanProperty isSimulationDoneProperty() {
        return isSimulationDone;
    }

    public void setIsSimulationDone(Boolean status) {
        this.isSimulationDone.set(status);
    }

    public ScatterChart<Number, Number> getEntitiesAmountScatter() {
        return entitiesAmountScatter;
    }

    public void setEntitiesAmountScatter(ScatterChart<Number, Number> entitiesAmountScatter) {
        this.entitiesAmountScatter = entitiesAmountScatter;
    }

    @Override
    public String toString() {
        return "Simulation " + getSimulationID();
    }
}
