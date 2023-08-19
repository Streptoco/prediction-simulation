package engine.xml;
import engine.exception.XMLDuplicateEntityPropertyName;
import engine.exception.XMLDuplicateEnvPropertyName;
import engine.exception.XMLFileException;
import engine.general.object.World;
import engine.worldbuilder.prdobjects.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlReader {
    public XmlReader() {}

    public World ReadXML(String filePath){
        if(!(filePath.endsWith(".xml"))) {
            throw new XMLFileException(filePath);
        }
        File file = new File(filePath);
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(PRDWorld.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(file);
            String propertyDuplicateName = CheckEnvProperties(aWholeNewWorld.getPRDEvironment().getPRDEnvProperty());
            if(propertyDuplicateName != null){
                throw new XMLDuplicateEnvPropertyName(filePath, propertyDuplicateName);
            }
            propertyDuplicateName = CheckEntityProperties(aWholeNewWorld.getPRDEntities().getPRDEntity());
            if(propertyDuplicateName != null) {
                throw new XMLDuplicateEntityPropertyName(filePath, propertyDuplicateName, aWholeNewWorld.getPRDEntities().getPRDEntity().get(0).getName()); //TODO: when there is more that 1 entity, need to find the problematic one
            }
            return engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    private String CheckEnvProperties(List<PRDEnvProperty> envProperties) {
        List<String> propertyName = new ArrayList<>();
        for(PRDEnvProperty property : envProperties) {
            propertyName.add(property.getPRDName());
        }
        Set<String> duplicates = new HashSet<>();
        for(String name : propertyName) {
            if(duplicates.contains(name)) {
                return name;
            }
            duplicates.add(name);
        }
        return null;
    }

    private String CheckEntityProperties(List<PRDEntity> prdEntity) {
        List<PRDEnvProperty> tempPRDList = new ArrayList<>();
        for(PRDEntity entity : prdEntity) {
            for(PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                PRDEnvProperty tmpProperty = new PRDEnvProperty();
                tmpProperty.setPRDName(property.getPRDName());
                tempPRDList.add(tmpProperty);
            }
            String duplicateName = CheckEnvProperties(tempPRDList);
            if(duplicateName != null) {
                return duplicateName;
            }
        }
        return null;
    }


}
