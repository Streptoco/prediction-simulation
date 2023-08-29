package engine.context.impl;

import engine.entity.impl.EntityDefinition;
import engine.general.object.Environment;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.property.api.PropertyInterface;

import java.util.List;

public class ContextImpl implements Context {
    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private Environment activeEnvironment;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, Environment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = null;
        this.activeEnvironment = activeEnvironment;
    }

    // Second c'tor with secondary entity and not fuck up the backward compatibility
    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance ,EntityInstanceManager entityInstanceManager, Environment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

    public String getEntityName() { return entityInstanceManager.getEntityName() ;}

    @Override
    public void removeEntity() {
        entityInstanceManager.killEntity(primaryEntityInstance);
    }

    @Override
    public PropertyInterface getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }
    public EntityDefinition getEntityDefinition() {
        return entityInstanceManager.getEntityDefinition();
    }

    @Override
    public List<EntityInstance> getInstancesList() {
        return entityInstanceManager.getInstances();
    }
}
