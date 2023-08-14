package engine.entity.impl;

import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceManager {
    private int countInstances;
    private List<EntityInstance> instances;

    public EntityInstanceManager() {
        countInstances = 0;
        instances = new ArrayList<>();
    }

    public EntityInstance create(EntityDefinition entityDefinition) {
        countInstances++;
        EntityInstance newInstance = new EntityInstance(entityDefinition, countInstances);
        instances.add(newInstance);

        for(PropertyInterface currentProperty : entityDefinition.getProps()) {
            Object propertyValue = currentProperty.getValue();
            PropertyInterface newPropertyInstance = null;
            switch (currentProperty.getPropertyType()) {
                case INT:
                    newPropertyInstance = new IntProperty((int)propertyValue, currentProperty.getName(), currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case DECIMAL:
                    newPropertyInstance = new DecimalProperty((double)propertyValue, currentProperty.getName(), currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case STRING:
                    newPropertyInstance = new StringProperty((String)propertyValue, currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case BOOLEAN:
                    newPropertyInstance = new BooleanProperty((boolean)propertyValue, currentProperty.getName(), currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                    default:
                        //TODO: handle error

            }
            newInstance.addProperty(newPropertyInstance);
        }
        return newInstance;
    }

    public List<EntityInstance> getInstances() { return instances; }

    public void killEntity(int id) {
        // Do something
    }

}
