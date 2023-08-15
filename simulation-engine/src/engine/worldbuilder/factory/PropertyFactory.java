package engine.worldbuilder.factory;

import engine.actions.expression.ReturnType;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;
import engine.worldbuilder.prdobjects.PRDProperty;

public class PropertyFactory {
    public static PropertyInterface BuildProperty(PRDProperty prdProperty) {
        String propertyName = prdProperty.getPRDName();
        double from = prdProperty.getPRDRange().getFrom();
        double to = prdProperty.getPRDRange().getTo();
        boolean isRandom = prdProperty.getPRDValue().isRandomInitialize();
        String propertyType = prdProperty.getType();
        ReturnType returnType = ReturnType.convert(propertyType);
        PropertyInterface resultProperty = null;
        switch (returnType) {
            case INT:
                int intFromValue = Integer.parseInt(prdProperty.getPRDValue().getInit());
                 resultProperty = new IntProperty(intFromValue, propertyName, from, to, isRandom);
                break;
            case DECIMAL:
                double doubleFromValue = Double.parseDouble(prdProperty.getPRDValue().getInit());
                 resultProperty = new DecimalProperty(doubleFromValue, propertyName, from, to, isRandom);
                 break;
            case BOOLEAN:
                boolean booleanFromValue = Boolean.parseBoolean(prdProperty.getPRDValue().getInit());
                resultProperty = new BooleanProperty(booleanFromValue, propertyName, from, to, isRandom);
                break;
            case STRING:
                resultProperty = new StringProperty(prdProperty.getPRDValue().getInit(), from, to, isRandom);
                break;
            default: // TODO: throw exception.
        }
        return resultProperty;
    }
}
