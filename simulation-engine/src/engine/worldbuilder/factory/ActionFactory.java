package engine.worldbuilder.factory;

import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.expression.Type;
import engine.action.impl.calculation.CalculationAction;
import engine.action.impl.condition.impl.Condition;
import engine.action.impl.condition.impl.ConditionAction;
import engine.action.impl.condition.impl.MultipleConditionAction;
import engine.action.impl.condition.impl.SingleCondition;
import engine.action.impl.increasedecrease.IncreaseDecreaseAction;
import engine.action.impl.kill.KillAction;
import engine.action.impl.proximity.ProximityAction;
import engine.action.impl.replace.ReplaceAction;
import engine.action.impl.replace.ReplaceMode;
import engine.action.impl.set.SetAction;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.exception.*;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDActions;
import engine.worldbuilder.prdobjects.PRDCondition;
import engine.worldbuilder.prdobjects.PRDDivide;
import engine.xml.NewXMLReader;

import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static ActionInterface BuildAction(PRDAction prdAction, String ruleName) {
        ActionType actionType = ActionType.convert(prdAction.getType());
        ActionInterface resultAction = null;
        switch (actionType) {
            case INCREASE:
            case DECREASE:
                Expression expression = new Expression(prdAction.getBy());
                CheckArgumentsTypeForNumbers(expression, ruleName);
                resultAction = new IncreaseDecreaseAction(prdAction.getProperty(),
                        expression, prdAction.getType(), prdAction.getEntity());
                SearchEntity(prdAction.getEntity(), ruleName);
                break;
            case CALCULATION:
                String calculationType = "";
                String arg1 = "", arg2 = "";
                if (prdAction.getPRDDivide() == null) {
                    calculationType = "multiply";
                    arg1 = prdAction.getPRDMultiply().getArg1();
                    arg2 = prdAction.getPRDMultiply().getArg2();
                } else if (prdAction.getPRDMultiply() == null) {
                    calculationType = "divide";
                    arg1 = prdAction.getPRDDivide().getArg1();
                    arg2 = prdAction.getPRDDivide().getArg2();
                }
                Expression arg1Expression = new Expression(arg1);
                Expression arg2Expression = new Expression(arg2);
                CheckArgumentsTypeForNumbers(arg1Expression, ruleName);
                CheckArgumentsTypeForNumbers(arg2Expression, ruleName);
                CheckIfResultPropExistAndInTheCorrectType(prdAction.getEntity(), prdAction.getResultProp(), ruleName);
                resultAction = new CalculationAction(prdAction.getResultProp(), calculationType, arg1Expression, arg2Expression, prdAction.getEntity());
                break;
            case CONDITION:
                List<ActionInterface> thenList = new ArrayList<>();
                List<ActionInterface> elseList = new ArrayList<>();
                List<PRDAction> prdThenList = prdAction.getPRDThen().getPRDAction();
                List<PRDAction> prdElseList = prdAction.getPRDElse() == null ? null : prdAction.getPRDElse().getPRDAction();
                for (PRDAction currentAction : prdThenList) {
                    thenList.add(ActionFactory.BuildAction(currentAction, ruleName));
                }
                if (prdElseList != null) {
                    for (PRDAction currentAction : prdElseList) {
                        elseList.add(ActionFactory.BuildAction(currentAction, ruleName));
                    }
                }

                if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
                    Expression conditionPropertyExpression = new Expression(prdAction.getPRDCondition().getProperty());
                    Expression conditionValueExpression = new Expression(prdAction.getPRDCondition().getValue());
                    CheckSingleAction(prdAction.getPRDCondition(), ruleName);
                    resultAction = new ConditionAction(conditionPropertyExpression, prdAction.getPRDCondition().getOperator(),
                            conditionValueExpression, thenList, elseList, prdAction.getEntity());
                } else if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("multiple")) {
                    CheckMultipleCondition(prdAction.getPRDCondition(), ruleName);
                    resultAction = new MultipleConditionAction(thenList, elseList, prdAction.getPRDCondition().getLogical(),
                            ConditionFactory.BuildConditionFromList(prdAction.getPRDCondition().getPRDCondition()), prdAction.getEntity());
                }
                break;
            case SET:
                Expression setExpression = new Expression(prdAction.getValue());
                resultAction = new SetAction(prdAction.getProperty(), setExpression, prdAction.getEntity());
                SearchEntityAndProperty(prdAction.getEntity(), prdAction.getProperty(), ruleName, prdAction.getValue());
                break;
            case KILL:
                resultAction = new KillAction(prdAction.getEntity());
                break;
            case REPLACE:
                resultAction = new ReplaceAction(ReplaceMode.convert(prdAction.getMode()), prdAction.getKill(), prdAction.getCreate());
                break;
            case PROXIMITY:
                // TODO: get first entity, get secondary entity, check for the depth (in the invokaction) and get list of actions.
                List<ActionInterface> actionsInCaseOfProximity = new ArrayList<>();
                List<PRDAction> prdActionsForProximityList = prdAction.getPRDActions().getPRDAction();
                for (PRDAction currentAction : prdActionsForProximityList) {
                    actionsInCaseOfProximity.add(ActionFactory.BuildAction(currentAction, ruleName));
                }
                // added all the actions to be performed into the list itself
                String sourceEntity = prdAction.getPRDBetween().getSourceEntity();
                String targetEntity = prdAction.getPRDBetween().getTargetEntity();
                Expression proximityExpression = new Expression(prdAction.getPRDEnvDepth().getOf());
                resultAction = new ProximityAction(actionType, sourceEntity, targetEntity, proximityExpression, actionsInCaseOfProximity);
                break;
        }
        if (prdAction.getPRDSecondaryEntity() != null && resultAction != null) {
            addSecondaryEntity(resultAction, prdAction.getPRDSecondaryEntity().getEntity(), prdAction.getPRDSecondaryEntity().getPRDSelection().getCount(), prdAction.getPRDSecondaryEntity().getPRDSelection().getPRDCondition());
        }
        return resultAction;
    }

    public static void CheckIfEnvPropertyExistAndInTheCorrectType(Expression expression) {
        String envVariableName = "";
        String expressionValue = (String) expression.getValue();
        boolean found = false;
        int startIndex = expressionValue.indexOf("(");
        int endIndex = expressionValue.indexOf(")");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            envVariableName = expressionValue.substring(startIndex + 1, endIndex);
        }
        for (PropertyInterface envProperty : NewXMLReader.envVariables) {
            if (envProperty.getName().equals(envVariableName)) {
                if (!(envProperty.getPropertyType().equals(ReturnType.INT) || envProperty.getPropertyType().equals(ReturnType.DECIMAL))) {
                    throw new XMLVariableTypeException("", expression.getReturnType(), envProperty.getPropertyType());
                } else {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new XMLEnvPropertyNotFound("", expressionValue);
        }
    }

    public static void CheckArgumentsTypeForNumbers(Expression expression, String ruleName) {
        if (!(expression.getType().equals(Type.NUMBER))) {
            String expressionValue = (String) expression.getValue();
            if (expressionValue.startsWith("environment(")) {
                CheckIfEnvPropertyExistAndInTheCorrectType(expression);
            } else if (expressionValue.startsWith("random(")) {
                //Random function only produce numbers
                return;
            } else if (expressionValue.startsWith("evaluate(") || expressionValue.startsWith("ticks(")) {
                String entityName = ExtractEntityName(expressionValue);
                String propertyName = ExtractPropertyName(expressionValue);
                CheckIfEntityAndPropertyExist(entityName, propertyName, ruleName);
            } else if (expressionValue.startsWith("percent(")) {
                //TODO: handle
            } else {
                throw new XMLVariableTypeException("", expressionValue, ReturnType.DECIMAL);
            }
        }
    }

    public static void CheckIfResultPropExistAndInTheCorrectType(String entityName, String entityProperty, String ruleName) {
        boolean found = false;
        for (EntityDefinition currentEntity : NewXMLReader.entityDefinitionList) {
            if (currentEntity.getName().equalsIgnoreCase(entityName)) {
                for (PropertyInterface currentProperty : currentEntity.getProps()) {
                    if (currentProperty.getName().equalsIgnoreCase(entityProperty)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new XMLEntityPropertyNotFound("", entityName, entityProperty, ruleName);
                }
            }
        }
        if (!found) {
            throw new XMLEntityNotFoundException("", ruleName, entityName);
        }

    }

    public static EntityDefinition SearchEntity(String entityName, String ruleName) {
        boolean found = false;
        EntityDefinition entityToReturn = null;
        for (EntityDefinition currentEntity : NewXMLReader.entityDefinitionList) {
            if (currentEntity.getName().equalsIgnoreCase(entityName)) {
                found = true;
                entityToReturn = currentEntity;
                break;
            }
        }
        if (!found) {
            throw new XMLEntityNotFoundException("", ruleName, entityName);
        } else {
            return entityToReturn;
        }

    }

    public static void SearchEntityAndProperty(String entityName, String propertyName, String ruleName, String value) {
        EntityDefinition currentEntity = SearchEntity(entityName, ruleName);
        boolean found = false;
        for (PropertyInterface property : currentEntity.getProps()) {
            if (property.getName().equalsIgnoreCase(propertyName)) {
                found = true;
                CheckPropertyAndValueType(property, value);
                break;
            }
        }
        if (!found) {
            throw new XMLEntityPropertyNotFound("", entityName, propertyName, ruleName);
        }
    }

    public static void CheckMultipleCondition(PRDCondition multipleCondition, String ruleName) {
        for (PRDCondition currentCondition : multipleCondition.getPRDCondition()) {
            if (currentCondition.getSingularity().equalsIgnoreCase("single")) {
                CheckSingleAction(currentCondition, ruleName);
            } else {
                CheckMultipleCondition(currentCondition, ruleName);
            }
        }
    }

    public static void CheckPropertyAndValueType(PropertyInterface property, String value) {
        boolean sameType;
        switch (property.getPropertyType()) {
            case INT:
            case DECIMAL:
                try {
                    double doubleValue = Double.parseDouble(value);
                    sameType = true;
                } catch (NumberFormatException e) {
                    sameType = false;
                }
                break;
            case BOOLEAN:
                sameType = value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
                break;
            default:
                sameType = true;
        }
        if (!sameType) {
            throw new XMLVariableTypeException("", value, property.getPropertyType());
        }
    }

    public static void CheckIfEntityAndPropertyExist(String entityName, String propertyName, String ruleName) {
        EntityDefinition currentEntity = SearchEntity(entityName, ruleName);
        boolean found = false;
        for (PropertyInterface property : currentEntity.getProps()) {
            if (property.getName().equalsIgnoreCase(propertyName)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new XMLEntityPropertyNotFound("", entityName, propertyName, ruleName);
        }
    }

    public static void addSecondaryEntity(ActionInterface action, String entityName, String count, PRDCondition prdCondition) {
        Condition condition = ConditionFactory.BuildCondition(prdCondition);
        action.addSecondEntity(entityName, count, condition);
    }

    public static String ExtractEntityName(String expression) {
        String entityName = "";
        int entityNameStartIndex, entityNameEndIndex;
        entityNameStartIndex = expression.indexOf("(");
        entityNameEndIndex = expression.indexOf(".");
        if (entityNameStartIndex != -1 && entityNameEndIndex != -1) {
            entityName = expression.substring(entityNameStartIndex + 1, entityNameEndIndex);
        }
        return entityName;
    }

    public static String ExtractPropertyName(String expression) {
        String propertyName = "";
        int propertyNameStartIndex, propertyNameEndIndex;
        propertyNameStartIndex = expression.indexOf(".");
        propertyNameEndIndex = expression.indexOf(")");
        if (propertyNameStartIndex != -1 && propertyNameEndIndex != -1) {
            propertyName = expression.substring(propertyNameStartIndex + 1, propertyNameEndIndex);
        }
        return propertyName;

    }

    public static boolean CheckConditionProperty(Expression expression, String ruleName) {
        String expressionValue = expression.getExpression();
        if (expressionValue.startsWith("evaluate(") || expressionValue.startsWith("ticks(")) {
            String entityName = ExtractEntityName(expressionValue);
            String propertyName = ExtractPropertyName(expressionValue);
            CheckIfEntityAndPropertyExist(entityName, propertyName, ruleName);
            return true;
        } else if (expressionValue.startsWith("percent(")) {
            //TODO: handle
            return true;
        } else if (expressionValue.startsWith("environment(")) {
            CheckIfEnvPropertyExistAndInTheCorrectType(expression);
            return true;
        } else if (expressionValue.startsWith("random(")) {
            //TODO: handle
            return true;
        } else {
            return false;
        }
    }

    public static void CheckSingleAction(PRDCondition condition, String ruleName) {
        Expression conditionPropertyExpression = new Expression(condition.getProperty());
        Expression conditionValueExpression = new Expression(condition.getValue());
        if (!CheckConditionProperty(conditionPropertyExpression, ruleName)) {
            SearchEntityAndProperty(condition.getEntity(), condition.getProperty(), ruleName, condition.getValue());
        }
        //TODO: Check if the value is in the same type as the property in case it is a function property
    }


}
