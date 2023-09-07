package enginewrapper;

import engine.general.object.Engine;

import javax.xml.bind.JAXBException;

public class EngineWrapper {
    public static void main(String[] args) {
        Engine engine = new Engine();
        try {
            engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-3.xml".trim());
            //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus.xml".trim());
            //engine.loadWorld("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\master-ex2.xml".trim());
            System.out.println("hey lol");
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
        engine.StartSimulation();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        engine.StartSimulation();

    }
}
