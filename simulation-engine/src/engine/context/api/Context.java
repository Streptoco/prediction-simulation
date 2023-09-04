package engine.context.api;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Environment;
import engine.grid.impl.Grid;
import engine.property.api.PropertyInterface;

import java.util.List;
import java.util.Map;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    EntityInstance getSecondaryEntityInstance();
    EntityInstance getInstance(String entityName);
    void removePrimaryEntity();
    PropertyInterface getEnvironmentVariable(String name);
    EntityDefinition getPrimaryEntityDefinition();
    List<EntityInstance> getInstancesList();
    String getPrimaryEntityName();
    Grid getGrid();
    Environment getEnv();
    Map<String, EntityInstanceManager> getManager();
    void addSecondEntity(EntityInstance secondaryEntity);
    void setGrid(Grid grid);
}