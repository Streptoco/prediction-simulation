package engine.general.object;

import engine.action.expression.ReturnType;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// for environmental variables...
//TODO: figure out how environment properties work and how I should store them
public class Environment {
    private final Map<String, PropertyInterface> envVariables;

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

    public void updateProperty(String name, int newValue) {
        PropertyInterface oldProperty = envVariables.get(name);
        PropertyInterface newProperty = new IntProperty(newValue, name, oldProperty.getFrom(), oldProperty.getTo(), oldProperty.getRandomStatus());
        envVariables.put(name, newProperty);
    }
    public void updateProperty(String name, double newValue) {
        PropertyInterface oldProperty = envVariables.get(name);
        PropertyInterface newProperty = new DecimalProperty(newValue, name, oldProperty.getFrom(), oldProperty.getTo(), oldProperty.getRandomStatus());
        envVariables.put(name, newProperty);
    }
    public void updateProperty(String name, boolean newValue) {
        PropertyInterface oldProperty = envVariables.get(name);
        PropertyInterface newProperty = new BooleanProperty(newValue, name, oldProperty.getFrom(), oldProperty.getTo(), oldProperty.getRandomStatus());
        envVariables.put(name, newProperty);
    }

    public void updateProperty(String name, String newValue) {
        PropertyInterface oldProperty = envVariables.get(name);
        PropertyInterface newProperty = new StringProperty(newValue, name, oldProperty.getFrom(), oldProperty.getTo(), oldProperty.getRandomStatus());
        envVariables.put(name, newProperty);
    }



    public List<String> GetAllEnvVariablesNames() {
        return new ArrayList<>(envVariables.keySet());
    }
    public List<PropertyInterface> GetAllEnvVariables() {
        return new ArrayList<>(envVariables.values());
    }
}
