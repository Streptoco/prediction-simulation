package engine.context.impl;

import engine.entity.impl.EntityDefinition;
import engine.general.object.Environment;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.grid.impl.Grid;
import engine.property.api.PropertyInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContextImpl implements Context {
    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntityInstance;
    //private EntityInstanceManager entityInstanceManager;
    private Map<String, EntityInstanceManager> entityInstanceManager;

    private Environment activeEnvironment;

    private Grid grid = null;

    public ContextImpl(EntityInstance primaryEntityInstance, Map<String, EntityInstanceManager> entityInstanceManager, Environment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = null;
        this.activeEnvironment = activeEnvironment;
    }

    // Second c'tor with secondary entity and not fuck up the backward compatibility
    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance, Map<String, EntityInstanceManager> entityInstanceManager, Environment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.activeEnvironment = activeEnvironment;
    }

    // Third c'tor with secondary entity and grid
    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntityInstance, Map<String, EntityInstanceManager> entityInstanceManager, Environment activeEnvironment, Grid grid) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.secondaryEntityInstance = secondaryEntityInstance;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
    }

    @Override
    public EntityInstance getInstance(String entityName) {
        if(entityName.equalsIgnoreCase(primaryEntityInstance.getEntityName())) {
            return primaryEntityInstance;
        } else if(entityName.equalsIgnoreCase(secondaryEntityInstance.getEntityName())) {
            return secondaryEntityInstance;
        } else {
            throw new RuntimeException("Entity name was not as the primary entity name or the secondary entity name");
        }
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    public EntityInstance getSecondaryEntityInstance() {
        return secondaryEntityInstance;
    }

    public String getPrimaryEntityName() {
        return this.primaryEntityInstance.getEntityName();
    }

    public String getSecondaryEntityName() {
        return this.secondaryEntityInstance.getEntityName();
    }

    @Override
    public void removePrimaryEntity() {
        entityInstanceManager.get(primaryEntityInstance.getEntityName()).killEntity(primaryEntityInstance);
    }

    public void removeSecondaryEntity() {
        entityInstanceManager.get(secondaryEntityInstance.getEntityName()).killEntity(secondaryEntityInstance);
    }

    @Override
    public PropertyInterface getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }

    public EntityDefinition getPrimaryEntityDefinition() {
        return entityInstanceManager.get(primaryEntityInstance.getEntityName()).getEntityDefinition();
    }
    public EntityDefinition getSecondaryEntityDefinition() {
        return entityInstanceManager.get(secondaryEntityInstance.getEntityName()).getEntityDefinition();
    }

    @Override
    public List<EntityInstance> getInstancesList() {

        List<EntityInstance> resultList = new ArrayList<>();
        for (Map.Entry<String, EntityInstanceManager> entry : this.entityInstanceManager.entrySet()) {
            resultList.addAll(entry.getValue().getInstances());
        }
        return resultList;
    }

    @Override
    public Grid getGrid() {
        if (grid != null) {
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
    public Map<String, EntityInstanceManager> getManager() {
        return this.entityInstanceManager;
    }

    public void setGrid(Grid grid) {
        if (this.grid == null) {
            this.grid = grid;
        }
    }
}
