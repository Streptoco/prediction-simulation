package engine.worldbuilder.factory;

        import engine.World;
        import engine.actions.expression.ReturnType;
        import engine.properties.api.PropertyInterface;
        import engine.properties.impl.BooleanProperty;
        import engine.properties.impl.DecimalProperty;
        import engine.properties.impl.IntProperty;
        import engine.properties.impl.StringProperty;
        import engine.worldbuilder.prdobjects.PRDEnvProperty;
        import engine.worldbuilder.prdobjects.PRDProperty;
        import engine.worldbuilder.prdobjects.PRDValue;

        import java.util.Random;

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
                if (isRandom) {
                    double randomValue = World.NumberRandomGetter(from, to);
                    resultProperty = new IntProperty((int) randomValue, propertyName, from, to, isRandom);
                }
                else {
                    int intFromValue = Integer.parseInt(prdProperty.getPRDValue().getInit());
                    resultProperty = new IntProperty(intFromValue, propertyName, from, to, isRandom);
                }
                break;
            case DECIMAL:
                if (isRandom) {
                    double randomValue = World.NumberRandomGetter(from, to);
                    resultProperty = new DecimalProperty(randomValue, propertyName, from, to, isRandom);
                }
                else {
                    double doubleFromValue = Double.parseDouble(prdProperty.getPRDValue().getInit());
                    resultProperty = new DecimalProperty(doubleFromValue, propertyName, from, to, isRandom);
                }
                break;
            case BOOLEAN:
                if (isRandom) {
                    boolean randomValue = World.BooleanRandomGetter();
                    resultProperty = new BooleanProperty(randomValue,propertyName,from,to,isRandom);
                }
                else {
                    boolean booleanFromValue = Boolean.parseBoolean(prdProperty.getPRDValue().getInit());
                    resultProperty = new BooleanProperty(booleanFromValue, propertyName, from, to, isRandom);
                }
                break;
            case STRING:
                resultProperty = new StringProperty(prdProperty.getPRDValue().getInit(), from, to, isRandom);
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
        String tempValString = String.valueOf(prdEnvProperty.getPRDRange().getFrom());
        PRDValue tempVal = new PRDValue();
        tempVal.setInit(tempValString);
        property.setPRDValue(tempVal);
        return BuildProperty(property);
    }
}
