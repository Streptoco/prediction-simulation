//package handler.controller;
//
//import engine.general.object.Engine;
//import javafx.concurrent.Task;
//
//public class CounterTask extends Task<Double> {
//
//    private Engine engine;
//    private int simID;
//
//    public CounterTask(Engine engine, int simID) {
//        this.engine = engine;
//        this.simID = simID;
//    }
//
//    @Override
//    protected Double call(){
//        double elapsedSeconds = 0;
//
//        while (engine.getSimulationManager().getSimulation(simID).getTermination().getTermination
//                (engine.getSimulationManager().getSimulationTick(simID), (engine.getSimulationManager().getSimulation(simID).getCurrentTime()))) {
//            long currentTimeMillis = engine.getSimulationManager().getSimulationRunningTimeInMillis(simID);
//            elapsedSeconds = (currentTimeMillis) / 1000.0; // Convert milliseconds to seconds
//
//            // Update the UI with the elapsed time (on the JavaFX application thread)
//            updateValue(elapsedSeconds);
//        }
//
//        return elapsedSeconds;
//    }
//}