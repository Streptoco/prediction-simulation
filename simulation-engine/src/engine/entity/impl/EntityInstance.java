package engine.entity.impl;

import engine.entity.api.EntityInstanceInterface;
import engine.properties.api.PropertyInterface;

import java.util.HashMap;
import java.util.Map;

public class EntityInstance implements EntityInstanceInterface {
    private EntityDefinition entityDefinition;
    private int id;
    private Map<String, PropertyInterface> properties;

    public EntityInstance(EntityDefinition entityDefinition, int id) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public PropertyInterface getPropertyByName(String propertyName) {
        if (!properties.containsKey(propertyName)) {
            throw new IllegalArgumentException("for entity of type " + entityDefinition.getName() + " has no property named " + propertyName);
        }

        return properties.get(propertyName);
    }

    public void addProperty(PropertyInterface property) {
        properties.put(property.getName(), property);
    }
}
