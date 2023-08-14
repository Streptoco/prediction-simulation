package engine.context.api;

import engine.entity.impl.EntityInstance;
import engine.properties.api.PropertyInterface;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInterface getEnvironmentVariable(String name);
}