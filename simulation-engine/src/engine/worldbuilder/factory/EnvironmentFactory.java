package engine.worldbuilder.factory;

import engine.Environment;
import engine.actions.expression.ReturnType;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.IntProperty;
import engine.worldbuilder.prdobjects.PRDEnvProperty;
import engine.worldbuilder.prdobjects.PRDEvironment;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentFactory {
    public static Environment BuildEnvironment(PRDEvironment prdEnv) {
        List<PropertyInterface> envProperties = new ArrayList<>();
        ReturnType propertyType;
        for(PRDEnvProperty prdEnvProperty : prdEnv.getPRDEnvProperty()) {
            propertyType = ReturnType.convert(prdEnvProperty.getType());
            switch (propertyType) {
                case INT:
                    envProperties.add(PropertyFactory.BuildProperty(prdEnvProperty));
            }
        }
    }
}
