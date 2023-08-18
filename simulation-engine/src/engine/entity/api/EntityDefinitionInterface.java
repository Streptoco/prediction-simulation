package engine.entity.api;

import engine.property.api.PropertyInterface;

import java.util.ArrayList;

public interface EntityDefinitionInterface {
    String getName();
    int getPopulation();
    ArrayList<PropertyInterface> getProps();
}
