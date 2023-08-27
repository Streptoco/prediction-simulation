package engine.xml;

import engine.exception.XMLException;
import engine.exception.XMLFileException;
import engine.general.object.World;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDWorld;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NewXMLReader {
    private String filePath;
    private World world;

    public static List<PropertyInterface> envVariables = null;

    public NewXMLReader() {
    }

    public World ReadXML(String filePath) throws JAXBException {
        this.filePath = filePath;
        if (!(filePath.endsWith(".xml"))) {
            throw new XMLFileException(filePath);
        }
        File file = new File(filePath);
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(PRDWorld.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(file);
            this.world = engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (XMLException e) {
            System.out.println(e.getMessage() + " in the XML file " + filePath);
        }


        return world;
    }

}
