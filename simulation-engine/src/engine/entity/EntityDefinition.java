package engine.entity;

import engine.properties.api.AbstractProperty;

import java.util.ArrayList;

public class EntityDefinition {
    private String name;
    private int population;
    private ArrayList<AbstractProperty> propertyList;

    public EntityDefinition(String name, int population){
        this.name = name;
        this.population = population;
    }

    public void setProperties(ArrayList<AbstractProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public AbstractProperty getPropertyByName(String propertyName) {
        for (AbstractProperty property : propertyList) {
            if (property.getName().equals(propertyName)) {
                return property;
            }
        }
        return null;
    }
}
