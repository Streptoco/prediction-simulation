package engine.entity.impl;

import engine.entity.api.EntityDefinitionInterface;
import engine.property.api.PropertyInterface;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class EntityDefinition implements EntityDefinitionInterface {
    private String name;
    private int population;
    private List<PropertyInterface> propertyList;

    private IntegerProperty populationProperty;

    public EntityDefinition(String name, int population){
        this.name = name;
        this.population = population;
        propertyList = new ArrayList<>();
        populationProperty = new SimpleIntegerProperty();
    }

    public int getPopulationProperty() {
        return populationProperty.get();
    }

    public IntegerProperty populationPropertyProperty() {
        return populationProperty;
    }

    public void setPopulationProperty(int populationProperty) {
        this.populationProperty.set(populationProperty);
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
        return name;
    }
}
