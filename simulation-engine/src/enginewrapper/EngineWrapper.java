package enginewrapper;

import engine.entity.impl.EntityInstance;
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
import java.util.List;

public class EngineWrapper {
    public static void main(String[] args) {
        NewXMLReader xmlReader = new NewXMLReader();
        try {
            World world = xmlReader.ReadXML("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex2-virus-modified.xml");
            world.createPopulationOfEntity(world.GetEntities().get(1), 25);
            world.createPopulationOfEntity(world.GetEntities().get(0), 1);
            world.placeEntityOnGrid(world.GetInstances("Healthy").getInstances().get(0), 0,0);
                world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(0), 2,2);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(1), 0,1);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(2), 0,2);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(3), 0,3);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(4), 0,4);

            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(5), 1,0);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(6), 1,1);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(7), 1,2);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(8), 1,3);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(9), 1,4);


            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(10), 2,0);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(11), 2,1);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(13), 2,3);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(14), 2,4);


            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(15), 3,0);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(16), 3,1);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(17), 3,2);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(18), 3,3);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(19), 3,4);

            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(20), 4,0);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(21), 4,1);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(22), 4,2);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(23), 4,3);
            world.placeEntityOnGrid(world.GetInstances("Sick").getInstances().get(24), 4,4);


            world.printGrid();

            List<EntityInstance> stamEntities = world.changeGrid(world.GetInstances("Healthy").getInstances().get(0), 0, 0);
            for(EntityInstance entity : stamEntities) {
                entity.setName("Health");
            }
            System.out.println("hey lol\n\n");
            world.printGrid();
            world.Run();
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
    }
}
