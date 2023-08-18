package engine.entity.api;

import engine.property.api.PropertyInterface;

public interface EntityInstanceInterface {
    int getId();
    PropertyInterface getPropertyByName(String propertyName);
    void addProperty(PropertyInterface property);
}
