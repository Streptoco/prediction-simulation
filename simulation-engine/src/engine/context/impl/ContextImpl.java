package engine.context.impl;

import engine.Environment;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.property.api.PropertyInterface;

public class ContextImpl implements Context {
    private EntityInstance primaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private Environment activeEnvironment;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, Environment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public void removeEntity() {
        entityInstanceManager.killEntity(primaryEntityInstance);
    }

    @Override
    public PropertyInterface getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }
}
