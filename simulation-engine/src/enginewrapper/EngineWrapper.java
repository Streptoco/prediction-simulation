package enginewrapper;

import engine.exception.XMLException;
import engine.general.object.Engine;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;

public class EngineWrapper {
    public static void main(String[] args) {
        int simID0 = 0, simID1 = 1, simID2 = 2;
        Engine engine = new Engine();
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-3.xml".trim());
        engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-2.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus.xml".trim());
        //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\master-ex2.xml".trim());
        try {
            simID0 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 30), simID0);
            engine.setupPopulation(new EntityAmountDTO("Sick", 3), simID0);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 1), simID0);

            simID1 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 4), simID1);
            engine.setupPopulation(new EntityAmountDTO("Sick", 6), simID1);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 2), simID1);

            simID2 = engine.setupSimulation();
            engine.setupPopulation(new EntityAmountDTO("Healthy", 70), simID1);
            engine.setupPopulation(new EntityAmountDTO("Sick", 0), simID1);
            engine.setupEnvProperties(new PropertyInitializeDTO("infection-proximity", 1), simID1);
            System.out.println("hey lol");
            engine.runSimulation(simID0);
            engine.runSimulation(simID1);
            engine.runSimulation(simID2);

            Thread.sleep(2000);
            engine.abortSimulation(simID0);


            Thread.sleep(1000);
            engine.pauseSimulation(simID2);

            engine.simulationManualStep(simID2);
            engine.simulationManualStep(simID2);

            Thread.sleep(1000);
            engine.stopSimulation(simID1);
            engine.resumeSimulation(simID2);
            Thread.sleep(3000);
            engine.stopSimulation(simID2);
        } catch (JAXBException | XMLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}
