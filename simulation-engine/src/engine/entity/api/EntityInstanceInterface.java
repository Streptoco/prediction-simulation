package engine.entity.api;

import engine.properties.api.PropertyInterface;

public interface EntityInstanceInterface {
    int getId();
    PropertyInterface getPropertyByName(String propertyName);
    void addProperty(PropertyInterface property);
}
