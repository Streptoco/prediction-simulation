package engine;

import engine.actions.expression.ReturnType;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;

import java.util.HashMap;
import java.util.Map;

// for environmental variables...
//TODO: figure out how environment properties work and how I should store them
public class Environment {
    private static Map<String, PropertyInterface> envVariables = null;

    public Environment(){
        envVariables = new HashMap<>();
    }

    public static Object environmentGetter(String propertyName) {
        return envVariables.get(propertyName).getValue();
    }

    public static ReturnType propertyTypeGetter (String propertyName) {
        return envVariables.get(propertyName).getPropertyType();
    }

    public PropertyInterface getProperty(String name) {
        return envVariables.get(name);
    }

    public void setProperty(PropertyInterface property) {
        envVariables.put(property.getName(), property);
    }
}
