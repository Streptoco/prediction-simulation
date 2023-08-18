package engine.context.api;

import engine.entity.impl.EntityInstance;
import engine.property.api.PropertyInterface;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity();
    PropertyInterface getEnvironmentVariable(String name);
}