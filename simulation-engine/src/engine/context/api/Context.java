package engine.context.api;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.general.object.Environment;
import engine.grid.impl.Grid;
import engine.property.api.PropertyInterface;

import java.util.List;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity();
    PropertyInterface getEnvironmentVariable(String name);
    EntityDefinition getEntityDefinition();
    List<EntityInstance> getInstancesList();
    String getEntityName();
    Grid getGrid();
    Environment getEnv();
}