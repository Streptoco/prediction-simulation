package engine.entity.impl;

import engine.general.object.World;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceManager {
    private int countInstances;
    private int numberOfAllInstances;
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
                    if (currentProperty.getRandomStatus()) {
                        propertyValue = (int) World.NumberRandomGetter(currentProperty.getFrom(),currentProperty.getTo());
                    }
                    newPropertyInstance = new IntProperty((int)propertyValue, currentProperty.getName(), currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case DECIMAL:
                    if (currentProperty.getRandomStatus()) {
                        propertyValue = World.NumberRandomGetter(currentProperty.getFrom(),currentProperty.getTo());
                    }
                    newPropertyInstance = new DecimalProperty((double)propertyValue, currentProperty.getName(), currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case STRING:
                    newPropertyInstance = new StringProperty(currentProperty.getName(), (String) propertyValue, currentProperty.getFrom(), currentProperty.getTo(), currentProperty.getRandomStatus() );
                    break;
                case BOOLEAN:
                    if (currentProperty.getRandomStatus()) {
                        propertyValue = World.BooleanRandomGetter();
                    }
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

    public void killEntity(EntityInstance entityToKill) {
        //instances.remove(entityToKill);
        entityToKill.setDead();
        countInstances--;
    }

}
