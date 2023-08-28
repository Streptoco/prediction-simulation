package engine.xml;

import engine.exception.*;
import engine.general.object.World;
import engine.worldbuilder.prdobjects.*;
import enginetoui.dto.basic.*;

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
    private List<PRDEntity> prdEntityList;

    public XmlReader() {
    }

    public World ReadXML(String filePath) throws JAXBException {
       // filePath = filePath.replaceAll("\\s", "");
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
            String propertyDuplicateName = CheckEnvPropertiesNamesForDuplication(aWholeNewWorld.getPRDEnvironment().getPRDEnvProperty());
            if (propertyDuplicateName != null) {
                throw new XMLDuplicateEnvPropertyName(filePath, propertyDuplicateName);
            } else {
                this.envVariables = aWholeNewWorld.getPRDEnvironment().getPRDEnvProperty();
            }
            PropertyDuplicateNameDTO propertyDuplicate = CheckEntityPropertiesNamesForDuplication(aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (propertyDuplicate != null) {
                throw new XMLDuplicateEntityPropertyName(filePath, propertyDuplicate.propertyName, propertyDuplicate.entityName);
            } else {
                this.prdEntityList = aWholeNewWorld.getPRDEntities().getPRDEntity();
            }
            EntityNotFoundDTO entityNotFound = CheckInRulesActionsIfEntityExist(aWholeNewWorld.getPRDRules().getPRDRule(), aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (entityNotFound != null) {
                throw new XMLEntityNotFoundException(filePath, entityNotFound.ruleName, entityNotFound.entityName);
            }
            PropertyNotFoundDTO propertyNotFound = CheckInRulesActionIfEntityPropertyExist(aWholeNewWorld.getPRDRules().getPRDRule(), aWholeNewWorld.getPRDEntities().getPRDEntity());
            if (propertyNotFound != null) {
                throw new XMLRulePropertyNotFoundException(filePath, propertyNotFound.ruleName, propertyNotFound.entityName, propertyNotFound.propertyName);
            }
            ArgumentsInvalidDTO argumentsInvalidDTO = CheckIfArgumentsAreValid(aWholeNewWorld.getPRDRules().getPRDRule());
            if (argumentsInvalidDTO != null) {
                throw new XMLException(argumentsInvalidDTO.getName());
            }

            return engine.worldbuilder.factory.WorldFactory.BuildWorld(aWholeNewWorld);
        } catch (JAXBException e) {
            throw e;
        }
    }

    private ArgumentsInvalidDTO CheckIfArgumentsAreValid(List<PRDRule> prdRules) {
        ArgumentsInvalidDTO dtoToReturn = null;
        for (PRDRule rules : prdRules) {
            for (PRDAction actions : rules.getPRDActions().getPRDAction()) {
                if (actions.getType().equalsIgnoreCase("increase") ||
                        actions.getType().equalsIgnoreCase("decrease")) {
                    String toParse = actions.getBy();
                    if (toParse.startsWith("random(") || toParse.startsWith("environment(")) {
                        continue;
                    }
                    try {
                        int tryInt = Integer.parseInt(toParse);
                    } catch (NumberFormatException e) {
                        return new ArgumentsInvalidDTO(toParse);
                    }
                    try {
                        double tryDouble = Double.parseDouble(toParse);
                    } catch (NumberFormatException e) {
                        return new ArgumentsInvalidDTO(toParse);
                    }
                }
                if (actions.getType().equalsIgnoreCase("condition")) {
                    dtoToReturn = checkingArgumentsRecurse(actions);
                    if (dtoToReturn != null) {
                        return dtoToReturn;
                    }
                }
            }
        }
        return null;
    }

    ArgumentsInvalidDTO checkingArgumentsRecurse(PRDAction action) {
        if (action.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
            if (action.getPRDThen().getPRDAction().get(0).getType().equalsIgnoreCase("increase")
                    || action.getPRDThen().getPRDAction().get(0).getType().equalsIgnoreCase("decrease")) {
                String toParse = action.getPRDThen().getPRDAction().get(0).getBy();
                if (toParse.startsWith("random(") || toParse.startsWith("environment(")) {
                    return null;
                }
                try {
                    int tryInt = Integer.parseInt(toParse);
                } catch (NumberFormatException e) {
                    return new ArgumentsInvalidDTO("Error! the supplied argument isn't supported in the current action.\n" +
                            "current action: \"" + action.getPRDThen().getPRDAction().get(0).getType() + "\" argument: \"" + toParse+"\"");
                }
                try {
                    double tryDouble = Double.parseDouble(toParse);
                } catch (NumberFormatException e) {
                    throw new XMLException("Error");
                }
            }
        }
        return null;
    }


    private String CheckEnvPropertiesNamesForDuplication(List<PRDEnvProperty> envProperties) {
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

    private PropertyDuplicateNameDTO CheckEntityPropertiesNamesForDuplication(List<PRDEntity> prdEntity) {
        List<PRDEnvProperty> tempPRDList = new ArrayList<>();
        for (PRDEntity entity : prdEntity) {
            tempPRDList.clear();
            for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                PRDEnvProperty tmpProperty = new PRDEnvProperty();
                tmpProperty.setPRDName(property.getPRDName());
                tempPRDList.add(tmpProperty);
            }
            String duplicateName = CheckEnvPropertiesNamesForDuplication(tempPRDList);
            if (duplicateName != null) {
                return new PropertyDuplicateNameDTO(duplicateName, entity.getName());
                //throw new XMLDuplicateEntityPropertyName(filePath, propertyDuplicateName, aWholeNewWorld.getPRDEntities().getPRDEntity().get(0).getName()); //TODO: when there is more that 1 entity, need to find the problematic one
            }
        }
        return null;
    }

    private EntityNotFoundDTO CheckInRulesActionsIfEntityExist(List<PRDRule> prdRuleList, List<PRDEntity> prdEntity) {
        List<String> entityNames = new ArrayList<>();
        for (PRDEntity entity : prdEntity) {
            entityNames.add(entity.getName());
        }
        for (PRDRule prdRule : prdRuleList) {
            for (PRDAction action : prdRule.getPRDActions().getPRDAction()) {
                if (action.getType().equalsIgnoreCase("condition")) {
                    if (action.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
                        if (!entityNames.contains(action.getPRDCondition().getEntity())) {
                            return new EntityNotFoundDTO(prdRule.getName(), action.getPRDCondition().getEntity());
                        }
                    } else {
                        for (PRDCondition condition : action.getPRDCondition().getPRDCondition()) {
                            if (!entityNames.contains(condition.getEntity())) {
                                if (!condition.getSingularity().equalsIgnoreCase("multiple")) {
                                    return new EntityNotFoundDTO(prdRule.getName(), condition.getEntity());
                                }
                            }
                        }
                    }
                    for (PRDAction thenAction : action.getPRDThen().getPRDAction()) {
                        if (!entityNames.contains(thenAction.getEntity())) {
                            return new EntityNotFoundDTO(prdRule.getName(), thenAction.getEntity());
                        }
                    }
                    if (action.getPRDElse() != null) {
                        for (PRDAction elseAction : action.getPRDElse().getPRDAction()) {
                            if (!entityNames.contains(elseAction.getEntity())) {
                                return new EntityNotFoundDTO(prdRule.getName(), elseAction.getEntity());
                            }
                        }
                    }
                }
                if (!entityNames.contains(action.getEntity())) {
                    return new EntityNotFoundDTO(prdRule.getName(), action.getEntity());
                }
            }
        }
        return null;
    }

    private PropertyNotFoundDTO CheckInRulesActionIfEntityPropertyExist(List<PRDRule> prdRuleList, List<PRDEntity> prdEntity) {
        boolean propertyFound = false;
        String propertyName;
        PRDEntity entity = null;
        for (PRDRule rule : prdRuleList) {
            for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                for (PRDEntity currentEntity : prdEntity) {
                    if (currentEntity.getName().equals(action.getEntity())) {
                        entity = currentEntity;
                        break;
                    }
                }
                propertyName = action.getProperty();
                if (action.getType().equalsIgnoreCase("calculation")) {
                    PropertyNotFoundDTO calResult = CheckCalculationAction(action, entity, rule);
                    if (calResult != null) {
                        return calResult;
                    }

                } else if (action.getType().equalsIgnoreCase("condition")) {
                    if (action.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
                        propertyName = action.getPRDCondition().getProperty();
                        if (CheckSingleConditionAction(action.getPRDCondition())) {
                            if (CheckSingleConditionActions(action.getPRDThen().getPRDAction())) {
                                propertyFound = true;
                            } else {
                                propertyFound = false;
                            }
                            if(action.getPRDElse() != null) {
                                if (CheckSingleConditionActions(action.getPRDElse().getPRDAction())) {
                                    propertyFound = true;
                                } else {
                                    propertyFound = false;
                                }
                            }
                        } else {
                            propertyFound = false;
                        }
                    } else {
                        CheckMultipleConditionAction(action.getPRDCondition().getPRDCondition());
                    }
                } else if (action.getType().equalsIgnoreCase("increase") || action.getType().equalsIgnoreCase("decrease")) {
                    propertyFound = CheckIncreaseDecreaseAction(action);
                } else {
                    // This might be an irrelevant piece of code
                    for (PRDProperty property : entity.getPRDProperties().getPRDProperty()) {
                        if (action.getProperty().equals(property.getPRDName())) {
                            propertyFound = true;
                            break;
                        }
                    }
                }
                if (!propertyFound) {
                    return new PropertyNotFoundDTO(rule.getName(), entity.getName(), propertyName);
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
                if (!CheckCalActionArgs(calAction)) {
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
                if (mulAction.getArg1().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(mulAction.getArg1());
                } else if (mulAction.getArg2().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(mulAction.getArg2());
                } else return mulAction.getArg1().startsWith("random(") || mulAction.getArg2().startsWith("random(");
            }
        } else {
            PRDDivide divAction = calAction.getPRDDivide();
            try {
                double arg1 = Double.parseDouble(divAction.getArg1());
                double arg2 = Double.parseDouble(divAction.getArg2());

            } catch (NumberFormatException e) {
                if (divAction.getArg1().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(divAction.getArg1());
                } else if (divAction.getArg2().startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(divAction.getArg1());
                } else return divAction.getArg1().startsWith("random(") || divAction.getArg2().startsWith("random(");
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
        for (PRDEnvProperty property : this.envVariables) {
            if (property.getPRDName().equalsIgnoreCase(envVariableName)) {
                if (property.getType().equalsIgnoreCase("decimal") || property.getType().equalsIgnoreCase("float")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean CheckIncreaseDecreaseAction(PRDAction action) {
        try {
            double by = Double.parseDouble(action.getBy());
        } catch (NumberFormatException e) {
            if (action.getBy().startsWith("environment(")) {
                return CheckEnvVariablesFromCalAction(action.getBy());
            } else return action.getBy().startsWith("random(");
        }
        return true;
    }

    private boolean CheckSingleConditionAction(PRDCondition condition) {
        boolean entityFound = false, propertyFound = false, validOperator = true;
        PRDEntity relevantEntity = null;
        if (condition.getSingularity().equalsIgnoreCase("single")) {
            //Find the relevant entity for the condition
            for (PRDEntity entity : prdEntityList) {
                if (condition.getEntity().equalsIgnoreCase(entity.getName())) {
                    entityFound = true;
                    relevantEntity = entity;
                    break;
                }
            }
            if (!entityFound) {
                return false;
            }
            //Find the relevant property of the entity for the condition
            for (PRDProperty entityProperty : relevantEntity.getPRDProperties().getPRDProperty()) {
                if (entityProperty.getPRDName().equalsIgnoreCase(condition.getProperty())) {
                    propertyFound = CheckValidityOfPropertyTypeAndValue(entityProperty, condition.getValue());
                    ///Check validity of the operator
                    if (condition.getOperator().equalsIgnoreCase("bt") || condition.getOperator().equalsIgnoreCase("lt")) {
                        if (entityProperty.getType().equalsIgnoreCase("boolean") || entityProperty.getType().equalsIgnoreCase("string")) {
                            validOperator = false;
                        }
                    }
                    break;
                }
            }
            return propertyFound && validOperator;

        }
        return false;
    }

    private boolean CheckValidityOfPropertyTypeAndValue(PRDProperty property, String value) {
        if (property.getType().equalsIgnoreCase("decimal") || property.getType().equalsIgnoreCase("float")) {
            try {
                double by = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                if (value.startsWith("environment(")) {
                    return CheckEnvVariablesFromCalAction(value);
                } else return value.startsWith("random(");
            }
            return true;
        } else if (property.getType().equalsIgnoreCase("boolean")) {
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return true;
            } else if (value.startsWith("environment(")) {
                String envVariableName = "";
                int startIndex = value.indexOf("(");
                int endIndex = value.indexOf(")");
                if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                    envVariableName = value.substring(startIndex + 1, endIndex);
                }
                for (PRDEnvProperty envProperty : this.envVariables) {
                    if (envProperty.getPRDName().equalsIgnoreCase(envVariableName)) {
                        return envProperty.getType().equalsIgnoreCase("boolean");
                    }
                }

            }
        }
        return true;
    }


    private boolean CheckSingleConditionActions(List<PRDAction> condition) {
        if (condition == null) {
            return true;
        }
        for (PRDAction action : condition) {
            if (action.getType().equalsIgnoreCase("increase") || action.getType().equalsIgnoreCase("decrease")) {
                return CheckIncreaseDecreaseAction(action);
            } else if (action.getType().equalsIgnoreCase("calculation")) {
                return CheckCalActionArgs(action);
            }
        }
        return true;
    }

    private void CheckMultipleConditionAction(List<PRDCondition> conditions) {
        for (PRDCondition action : conditions) {
            if (action.getSingularity().equalsIgnoreCase("single")) {
                if (!CheckSingleConditionAction(action)) {
                    throw new XMLException(xmlPath + "\nThere was a problem with multiple condition");
                }
            } else {
                CheckMultipleConditionAction(action.getPRDCondition());
            }
        }
    }


}
