package engine.worldbuilder.factory;

import engine.general.object.World;
import engine.action.expression.ReturnType;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;
import engine.worldbuilder.prdobjects.*;
public class PropertyFactory {
    public static PropertyInterface BuildProperty(PRDProperty prdProperty) {
        String propertyName = prdProperty.getPRDName();
        double from = 0;
        double to = 0;
        boolean isRandom;
        if (prdProperty.getPRDValue() != null) {
            isRandom = prdProperty.getPRDValue().isRandomInitialize();
        } else {
            isRandom = true; //is true because env properties are need to be set by the user, and if not to be generated randomly
        }
        String propertyType = prdProperty.getType();
        ReturnType returnType = ReturnType.convert(propertyType);
        if (returnType == ReturnType.INT || returnType == ReturnType.DECIMAL) {
            if(prdProperty.getPRDRange() != null) {
                from = prdProperty.getPRDRange().getFrom();
                to = prdProperty.getPRDRange().getTo();
            } else { // In case range is not given, the range is limitless
                from = 0; // is it need to be 0 or the minimum value?
                to = Integer.MAX_VALUE;
            }
        }
        PropertyInterface resultProperty = null;
        switch (returnType) {
            case INT:
                if (isRandom) {
                    double randomValue = World.NumberRandomGetter(from, to); // TODO: change to minimal values.
                    resultProperty = new IntProperty((int) randomValue, propertyName, from, to, isRandom);
                } else {
                    double doubleFromValue = Double.parseDouble(prdProperty.getPRDValue().getInit());
                    int intFromValue = (int) doubleFromValue;
                    resultProperty = new IntProperty(intFromValue, propertyName, from, to, isRandom);
                }
                break;
            case DECIMAL:
                if (isRandom) {
                    double randomValue = World.NumberRandomGetter(from, to);
                    resultProperty = new DecimalProperty(randomValue, propertyName, from, to, isRandom);
                } else {
                    double doubleFromValue = Double.parseDouble(prdProperty.getPRDValue().getInit());
                    resultProperty = new DecimalProperty(doubleFromValue, propertyName, from, to, isRandom);
                }
                break;
            case BOOLEAN:
                if (isRandom) {
                    boolean randomValue = World.BooleanRandomGetter();
                    resultProperty = new BooleanProperty(randomValue, propertyName, from, to, isRandom);
                } else {
                    boolean booleanFromValue = Boolean.parseBoolean(prdProperty.getPRDValue().getInit());
                    resultProperty = new BooleanProperty(booleanFromValue, propertyName, from, to, isRandom);
                }
                break;
            case STRING:
                if (isRandom) {
                    resultProperty = new StringProperty(propertyName, World.StringRandomGetter(), from, to, isRandom);
                } else {
                    resultProperty = new StringProperty(propertyName, prdProperty.getPRDValue().getInit(), from, to, isRandom);
                }
                break;
            default: // TODO: throw exception.
        }
        return resultProperty;
    }

    public static PropertyInterface BuildProperty(PRDEnvProperty prdEnvProperty) {
        PRDProperty property = new PRDProperty();
        property.setPRDName(prdEnvProperty.getPRDName());
        property.setType(prdEnvProperty.getType());
        property.setPRDRange(prdEnvProperty.getPRDRange());
        //property need to value in order to use the upper BuildProperty function
        if(property.getPRDRange() != null) {
            if (property.getType().equalsIgnoreCase("decimal") || property.getType().equalsIgnoreCase("float")) {
                String tempValString = String.valueOf(prdEnvProperty.getPRDRange().getFrom());
                PRDValue tempVal = new PRDValue();
                tempVal.setInit(tempValString);
                property.setPRDValue(tempVal);
            }
        }
        return BuildProperty(property);
    }
}
