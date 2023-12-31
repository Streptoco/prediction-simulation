package engine.entity.impl;

import engine.entity.api.EntityInstanceInterface;
import engine.grid.api.Coordinate;
import engine.property.api.PropertyInterface;

import java.util.*;

public class EntityInstance implements EntityInstanceInterface {
    private EntityDefinition entityDefinition;
    private final int id;
    private final Map<String, PropertyInterface> properties;
    private boolean isAlive;
    private Coordinate position;
    String entityName;


    public EntityInstance(EntityDefinition entityDefinition, int id) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
        this.isAlive = true;
        this.entityName = entityDefinition.getName();
    }

    public EntityInstance(EntityDefinition entityDefinition, int id, Coordinate position) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
        this.isAlive = true;
        this.position = position;
        this.entityName = entityDefinition.getName();
    }


    public int getId() {
        return id;
    }

    public PropertyInterface getPropertyByName(String propertyName) {
        if (!properties.containsKey(propertyName)) {
//            throw new IllegalArgumentException("for entity of type " + entityDefinition.getName() + " has no property named " + propertyName);
            return null;
        }

        return properties.get(propertyName);
    }

    public void addProperty(PropertyInterface property) {
        properties.put(property.getName(), property);
    }

    public void setDead() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public List<String> getPropertiesName() {
        return new ArrayList<>((properties.keySet()));
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void setPosition(int row, int col) {
        setPosition(new Coordinate(row, col));
    }

    public String getEntityName() {
        return entityName;
    }

    public List<PropertyInterface> getProps() {
        return entityDefinition.getProps();
    }
}
