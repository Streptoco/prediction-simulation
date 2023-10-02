package engine.xml;

import engine.entity.impl.EntityDefinition;
import engine.exception.XMLException;
import engine.exception.XMLFileException;
import engine.general.object.World;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDWorld;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class NewXMLReader {
    private File filePath;

    private InputStream file;
    private World world;

    public static List<PropertyInterface> envVariables = null;

    public static List<EntityDefinition> entityDefinitionList = null;

    public NewXMLReader() {
    }

    public World ReadXML(File filePath) throws JAXBException, XMLException {
        this.filePath = filePath;
        if (!(filePath.getName().endsWith(".xml"))) {
            throw new XMLFileException(filePath.getName());
        }
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(PRDWorld.class);
        Unmarshaller u = jaxbContext.createUnmarshaller();
        PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(filePath);
        this.world = engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        return world;
    }

    public World ReadXMLFromStream(InputStream file) throws JAXBException {
        this.file = file;
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(PRDWorld.class);
        Unmarshaller u = jaxbContext.createUnmarshaller();
        PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(file);
        this.world = engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        return world;

    }

}
