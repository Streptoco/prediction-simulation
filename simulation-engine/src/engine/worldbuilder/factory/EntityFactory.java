package engine.worldbuilder.factory;

import engine.entity.impl.EntityDefinition;
import engine.worldbuilder.prdobjects.PRDEntity;
import engine.worldbuilder.prdobjects.PRDProperty;

public class EntityFactory {
    public static EntityDefinition BuildEntity(PRDEntity prdEntity) {
        EntityDefinition entityDefinition = new EntityDefinition(prdEntity.getName(), prdEntity.getPRDPopulation());
        for (PRDProperty property : prdEntity.getPRDProperties().getPRDProperty()) {
            entityDefinition.addProperty(PropertyFactory.BuildProperty(property));
        }
        return entityDefinition;
    }
}
