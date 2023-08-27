package engine.worldbuilder.factory;

import engine.general.object.Environment;
import engine.action.expression.ReturnType;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;
import engine.worldbuilder.prdobjects.*;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentFactory {
    public static Environment BuildEnvironment(PRDEnvironment prdEnv) {
        List<PropertyInterface> envProperties = new ArrayList<>();
        Environment resultEnv = new Environment();
        ReturnType propertyType;
        for(PRDEnvProperty prdEnvProperty : prdEnv.getPRDEnvProperty()) {
            propertyType = ReturnType.convert(prdEnvProperty.getType());
            switch (propertyType) {
                case INT:
                    envProperties.add((IntProperty)PropertyFactory.BuildProperty(prdEnvProperty));
                    break;
                case DECIMAL:
                    envProperties.add((DecimalProperty)PropertyFactory.BuildProperty(prdEnvProperty));
                    break;
                case BOOLEAN:
                    envProperties.add((BooleanProperty)PropertyFactory.BuildProperty(prdEnvProperty));
                    break;
                case STRING:
                    envProperties.add((StringProperty)PropertyFactory.BuildProperty(prdEnvProperty));
                    break;
            }
        }

        for(PropertyInterface currentProperty : envProperties) {
            resultEnv.setProperty(currentProperty);
        }
        return resultEnv;
    }
}
