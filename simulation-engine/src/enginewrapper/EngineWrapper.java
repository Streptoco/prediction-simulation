package enginewrapper;

import engine.exception.XMLException;
import engine.general.object.Engine;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import simulations.dto.PopulationsDTO;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;
import java.util.Map;

public class EngineWrapper {
    public static void main(String[] args) {
        int simID0 = 0, simID1 = 1, simID2 = 2;
        Engine engine = new Engine();
        engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\prediction-simulation\\simulation-engine\\TestFiles\\ex2-virus-modified-3.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-2.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\master-ex2.xml".trim());
        try {
            simID0 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 30), simID0);
            engine.setupPopulation(new EntityAmountDTO("Sick", 3), simID0);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 2), simID0);

            simID1 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 5), simID1);
            engine.setupPopulation(new EntityAmountDTO("Sick", 5), simID1);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 1), simID1);

            simID2 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 70), simID2);
            engine.setupPopulation(new EntityAmountDTO("Sick", 15), simID2);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 1), simID2);
            engine.runSimulation(simID0);
            engine.runSimulation(simID1);
            engine.runSimulation(simID2);

            Thread.sleep(90);
            engine.abortSimulation(simID0);
            SimulationStatusDTO sim01 = engine.getSimulationDetails(simID1);
            Thread.sleep(500);
            engine.pauseSimulation(simID2);
            Thread.sleep(500);
            SimulationStatusDTO sim02 = engine.getSimulationDetails(simID2);
            engine.simulationManualStep(simID2);
            engine.simulationManualStep(simID2);
            Thread.sleep(1000);
            //engine.stopSimulation(simID1);
            engine.resumeSimulation(simID2);
//            Thread.sleep(3000);
//            engine.stopSimulation(simID2);

            SimulationStatusDTO sim00 = engine.getSimulationDetails(simID0);
            System.out.println("hey lol");
            System.out.println(engine.getConsistency("Sick", "vacinated", simID2));
            Map<Integer, PopulationsDTO> amounts = engine.getEntitiesAmountPerTick(simID2);
            for(Map.Entry<Integer, PopulationsDTO> entry : amounts.entrySet()) {
                System.out.println(entry.getKey() + ". ");
                for(Map.Entry<String, Integer> currentEntity : entry.getValue().getEntities().entrySet()) {
                        System.out.println("\t" + currentEntity.getKey() + ": " + currentEntity.getValue());
                }
                System.out.println();
            }
            engine.reRunSimulation(simID0);
        } catch (JAXBException | InterruptedException | RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}
