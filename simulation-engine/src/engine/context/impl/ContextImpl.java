package engine.context.impl;

import engine.entity.impl.EntityDefinition;
import engine.general.object.Environment;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.grid.impl.Grid;
import engine.property.api.PropertyInterface;

import java.util.List;

public class ContextImpl implements Context {
    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private Environment activeEnvironment;

    private Grid grid = null;

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

    // Third c'tor with secondary entity and grid
    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance ,EntityInstanceManager entityInstanceManager, Environment activeEnvironment, Grid grid) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
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

    @Override
    public Grid getGrid() {
        if(grid != null) {
            return this.grid;
        } else {
            //TODO: handle exception
            return null;
        }
    }

    @Override
    public Environment getEnv() {
        return activeEnvironment;
    }

    @Override
    public EntityInstanceManager getManager() {
        return this.entityInstanceManager;
    }
}
