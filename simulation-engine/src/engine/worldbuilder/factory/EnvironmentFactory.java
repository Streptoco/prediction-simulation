package engine.worldbuilder.factory;

import engine.Environment;
import engine.actions.expression.ReturnType;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;
import engine.worldbuilder.prdobjects.PRDEnvProperty;
import engine.worldbuilder.prdobjects.PRDEvironment;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentFactory {
    public static Environment BuildEnvironment(PRDEvironment prdEnv) {
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
