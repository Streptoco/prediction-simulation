package engine.entity.impl;

import engine.entity.api.EntityDefinitionInterface;
import engine.property.api.PropertyInterface;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinition implements EntityDefinitionInterface {
    private String name;
    private int population;
    private List<PropertyInterface> propertyList;

    public EntityDefinition(String name, int population){
        this.name = name;
        this.population = population;
        propertyList = new ArrayList<>();
    }

    public String getName() { return name; }

    public int getPopulation() { return population; }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<PropertyInterface> getProps() { return propertyList; }

    public void addProperty(PropertyInterface newProperty) {
        propertyList.add(newProperty);
    }

    @Override
    public String toString() {
        return "EntityDefinition{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", propertyList=" + propertyList +
                '}';
    }
}
