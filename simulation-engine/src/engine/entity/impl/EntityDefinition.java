package engine.entity.impl;

import engine.entity.api.EntityDefinitionInterface;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyInterface;
import engine.properties.api.PropertyType;

import java.util.ArrayList;

public class EntityDefinition implements EntityDefinitionInterface {
    private String name;
    private int population;
    private ArrayList<PropertyInterface> propertyList;

    public EntityDefinition(String name, int population){
        this.name = name;
        this.population = population;
        propertyList = new ArrayList<>();
    }

    public String getName() { return name; }

    public int getPopulation() { return population; }

    public ArrayList<PropertyInterface> getProps() { return propertyList; }

    public void addProperty(PropertyInterface newProperty) {
        propertyList.add(newProperty);
    }
}
