package enginewrapper;

import engine.exception.XMLException;
import engine.general.object.Engine;
import engine.general.object.World;
import engine.worldbuilder.prdobjects.PRDWorld;
import engine.xml.NewXMLReader;
import engine.xml.XmlReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class EngineWrapper {
    public static void main(String[] args) {
        NewXMLReader xmlReader = new NewXMLReader();
        try {
            World world = xmlReader.ReadXML("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-3.xml".trim());
            world.getEnvironment().updateProperty("infection-proximity", 1);
            world.createPopulationOfEntity(world.GetEntities().get(0), 15);
            world.createPopulationOfEntity(world.GetEntities().get(1), 2);
            System.out.println("hey lol");
            //world.Run();
            world.NewRun();
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}
