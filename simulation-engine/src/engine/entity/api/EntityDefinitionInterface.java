package engine.entity.api;

import engine.property.api.PropertyInterface;

import java.util.ArrayList;
import java.util.List;

public interface EntityDefinitionInterface {
    String getName();
    int getPopulation();
    List<PropertyInterface> getProps();
}
