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
            World world = xmlReader.ReadXML("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified-2.xml");
            world.createPopulationOfEntity(world.GetEntities().get(0), 7);
            world.createPopulationOfEntity(world.GetEntities().get(1), 3);
            System.out.println("hey lol");
            world.Run();
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}
