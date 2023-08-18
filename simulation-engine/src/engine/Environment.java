package engine;

import engine.action.expression.ReturnType;
import engine.property.api.PropertyInterface;

import java.util.HashMap;
import java.util.Map;

// for environmental variables...
//TODO: figure out how environment properties work and how I should store them
public class Environment {
    private Map<String, PropertyInterface> envVariables;

    public Environment(){
        envVariables = new HashMap<>();
    }

    public Object environmentGetter(String propertyName) {
        return envVariables.get(propertyName).getValue();
    }

    public  ReturnType propertyTypeGetter (String propertyName) {
        return envVariables.get(propertyName).getPropertyType();
    }

    public PropertyInterface getProperty(String name) {
        return envVariables.get(name);
    }

    public void setProperty(PropertyInterface property) {
        envVariables.put(property.getName(), property);
    }
}
