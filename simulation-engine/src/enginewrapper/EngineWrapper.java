package enginewrapper;

import engine.general.object.Engine;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;

public class EngineWrapper {
    public static void main(String[] args) {
        int simID1 = 0, simID2 = 1;
        Engine engine = new Engine();
        engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-3.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\master-ex2.xml".trim());
        try {
            simID1 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 15), simID1);
            engine.setupPopulation(new EntityAmountDTO("Sick", 3), simID1);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 1), simID1);

            simID2 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 4), simID2);
            engine.setupPopulation(new EntityAmountDTO("Sick", 6), simID2);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 2), simID2);
            System.out.println("hey lol");
            engine.runSimulation(simID1);
            engine.runSimulation(simID2);
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }

        try {
            Thread.sleep(50);
            engine.pauseSimulation(simID1);
            Thread.sleep(5000);
            engine.simulationManualStep(simID1);
            Thread.sleep(1000);
            engine.simulationManualStep(simID1);
            Thread.sleep(1000);
            engine.simulationManualStep(simID1);
            Thread.sleep(1000);
            engine.resumeSimulation(simID1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
