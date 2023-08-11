package engine.entity.api;

import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyInterface;

import java.util.ArrayList;

public interface EntityDefinitionInterface {
    String getName();
    int getPopulation();
    ArrayList<PropertyInterface> getProps();
}
