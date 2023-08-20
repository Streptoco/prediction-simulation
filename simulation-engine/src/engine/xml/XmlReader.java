package engine.xml;

import com.sun.org.apache.xerces.internal.xni.XNIException;
import engine.exception.*;
import engine.general.object.World;
import engine.worldbuilder.prdobjects.*;
import enginetoui.dto.basic.EntityNotFoundDTO;
import enginetoui.dto.basic.PropertyDuplicateNameDTO;
import enginetoui.dto.basic.PropertyNotFoundDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlReader {

    private String xmlPath;
    private List<PRDEnvProperty> envVariables;
    public XmlReader() {
    }

    public World ReadXML(String filePath) {
        filePath = filePath.replaceAll("\\s", "");
        if (!(filePath.endsWith(".xml"))) {
            throw new XMLFileException(filePath);
        }
        this.xmlPath = filePath;
        File file = new File(filePath);
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(PRDWorld.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            PRDWorld aWholeNewWorld = (PRDWorld) u.unmarshal(file);
            String propertyDuplicateName = CheckEnvProperties(aWholeNewWorld.getPRDEvironment().getPRDEnvProperty());
            if (propertyDuplicateName != null) {
                throw new XMLDuplicateEnvPropertyName(filePath, propertyDuplicateName);
            } else {
                this.envVariables = aWholeNewWorld.getPRDEvironment().getPRDEnvProperty();
            }
            PropertyDuplicateNameDTO propertyDuplicate = CheckEntityProperties(aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (propertyDuplicate != null) {
                throw new XMLDuplicateEntityPropertyName(filePath, propertyDuplicate.propertyName, propertyDuplicate.entityName);
            }
            EntityNotFoundDTO entityNotFound = CheckRuleEntity(aWholeNewWorld.getPRDRules().getPRDRule(), aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (entityNotFound != null) {
                throw new XMLEntityNotFoundException(filePath, entityNotFound.ruleName, entityNotFound.entityName);
            }
            PropertyNotFoundDTO propertyNotFound = CheckRuleEntityProperty(aWholeNewWorld.getPRDRules().getPRDRule(), aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (propertyNotFound != null) {
                throw new XMLRulePropertyNotFoundException(filePath, propertyNotFound.ruleName, propertyNotFound.entityName, propertyNotFound.propertyName);
            }

            return engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }

    private String CheckEnvProperties(List<PRDEnvProperty> envProperties) {
        List<String> propertyName = new ArrayList<>();
        for (PRDEnvProperty property : envProperties) {
            propertyName.add(property.getPRDName());
        }
        Set<String> duplicates = new HashSet<>();
        for (String name : propertyName) {
            if (duplicates.contains(name)) {
                return name;
                //throw new XMLDuplicateEnvPropertyName(filePath, propertyDuplicateName);
            }
            duplicates.add(name);
        }
        return null;
    }

    private PropertyDuplicateNameDTO CheckEntityProperties(List<PRDEntity> prdEntity) {
        List<PRDEnvProperty> tempPRDList = new ArrayList<>();
        for (PRDEntity entity : prdEntity) {
            for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                PRDEnvProperty tmpProperty = new PRDEnvProperty();
                tmpProperty.setPRDName(property.getPRDName());
                tempPRDList.add(tmpProperty);
            }
            String duplicateName = CheckEnvProperties(tempPRDList);
            if (duplicateName != null) {
                return new PropertyDuplicateNameDTO(duplicateName, entity.getName());
                //throw new XMLDuplicateEntityPropertyName(filePath, propertyDuplicateName, aWholeNewWorld.getPRDEntities().getPRDEntity().get(0).getName()); //TODO: when there is more that 1 entity, need to find the problematic one
            }
        }
        return null;
    }

    private EntityNotFoundDTO CheckRuleEntity(List<PRDRule> prdRuleList, List<PRDEntity> prdEntity) {
        List<String> entityNames = new ArrayList<>();
        for (PRDEntity entity : prdEntity) {
            entityNames.add(entity.getName());
        }
        for (PRDRule prdRule : prdRuleList) {
            for (PRDAction action : prdRule.getPRDActions().getPRDAction()) {
                if (!entityNames.contains(action.getEntity())) {
                    return new EntityNotFoundDTO(prdRule.getName(), action.getEntity());
                }
            }
        }
        return null;
    }


    private PropertyNotFoundDTO CheckRuleEntityProperty(List<PRDRule> prdRuleList, List<PRDEntity> prdEntity) {
        boolean propertyFound = false;
        PRDEntity entity = null;
        for (PRDRule rule : prdRuleList) {
            for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                for (PRDEntity currentEntity : prdEntity) {
                    if (currentEntity.getName().equals(action.getEntity())) {
                        entity = currentEntity;
                        break;
                    }
                }
                if (action.getType().equalsIgnoreCase("calculation")) {
                    PropertyNotFoundDTO calResult = CheckCalculationAction(action, entity, rule);
                    if (calResult != null) {
                        return calResult;
                    }

                } else if (action.getType().equalsIgnoreCase("condition")) {
                    continue;
                    //TODO: need to check the actions of then and else
                } else if(action.getType().equalsIgnoreCase("increase") || action.getType().equalsIgnoreCase("decrease")) {
                    propertyFound = true;
                }
                else {
                    for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                        if (action.getProperty().equals(property.getPRDName())) {
                            propertyFound = true;
                            break;
                        }
                    }
                }
                if (!propertyFound) {
                    return new PropertyNotFoundDTO(rule.getName(), entity.getName(), action.getProperty());
                }
            }
        }

        return null;
    }

    private PropertyNotFoundDTO CheckCalculationAction(PRDAction calAction, PRDEntity prdEntity, PRDRule rule) {
        String resultProp = calAction.getResultProp();
        boolean found = false;
        for (PRDProperty property : prdEntity.getPRDProperties().getPRDProperty()) {
            if (property.getPRDName().equals(resultProp)) {
                found = true;
                if(!CheckCalActionArgs(calAction)) {
                    throw new XMLFileException(xmlPath + "The arguments of " + calAction.getType() + "are not valid");
                }
                return null;
            }
        }
        if (!found) {
            return new PropertyNotFoundDTO(rule.getName(), prdEntity.getName(), calAction.getProperty());
        } else {
            return null;
        }
    }

    private boolean CheckCalActionArgs(PRDAction calAction) {
        if (calAction.getPRDMultiply() != null) {
            PRDMultiply mulAction = calAction.getPRDMultiply();
            try {
                double arg1 = Double.parseDouble(mulAction.getArg1());
                double arg2 = Double.parseDouble(mulAction.getArg2());

            } catch (NumberFormatException e) {
                if(mulAction.getArg1().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(mulAction.getArg1());
                } else if(mulAction.getArg2().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(mulAction.getArg2());
                }
                else if (mulAction.getArg1().startsWith("random(") || mulAction.getArg2().startsWith("random(")) {
                    return true;
                }
                return false;
            }
        }
        else {
            PRDDivide divAction = calAction.getPRDDivide();
            try {
                double arg1 = Double.parseDouble(divAction.getArg1());
                double arg2 = Double.parseDouble(divAction.getArg2());

            } catch (NumberFormatException e) {
                if(divAction.getArg1().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(divAction.getArg1());
                } else if(divAction.getArg2().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(divAction.getArg1());
                }
                else return divAction.getArg1().startsWith("random(") || divAction.getArg2().startsWith("random(");
            }

        }
        return true;
    }

    private boolean CheckEnvVariablesFromCalAction(String arg) {
        String envVariableName = "";
        int startIndex = arg.indexOf("(");
        int endIndex = arg.indexOf(")");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            envVariableName = arg.substring(startIndex + 1, endIndex);
        }
        for(PRDEnvProperty property : this.envVariables) {
            if(property.getPRDName().equalsIgnoreCase(envVariableName)) {
                if(property.getType().equalsIgnoreCase("decimal") || property.getType().equalsIgnoreCase("float")) {
                    return true;
                }
            }
        }
        return false;
    }


}
