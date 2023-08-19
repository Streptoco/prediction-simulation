package enginewrapper;

import engine.general.object.World;
import engine.worldbuilder.prdobjects.PRDWorld;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class EngineWrapper {
    public static void main(String[] args) {
        try {
            //File file = new File("C:\\Users\\AfikAtias\\Desktop\\Personal\\MTA\\Java\\Predictions\\ex1-cigarets.xml");
            File file = new File("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\simulation-engine\\src\\engine\\ex1-cigarets.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(file);
            System.out.println("lol hey");
            World world = engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
            world.Run();
            System.out.println("finished building!");

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
