package engine;

import engine.properties.Property;

import java.util.ArrayList;

public class EntityDefinition {
    private String name;
    private int population;
    private ArrayList<Property> propertyList;

    public EntityDefinition(String name, int population){
        this.name = name;
        this.population = population;
    }

    public void setProperties(ArrayList<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public Property getPropertyByName(String propertyName) {
        for (Property property : propertyList) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }
}
