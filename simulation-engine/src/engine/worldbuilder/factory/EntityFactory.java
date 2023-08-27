package engine.worldbuilder.factory;

import engine.entity.impl.EntityDefinition;
import engine.worldbuilder.prdobjects.*;

public class EntityFactory {
    public static EntityDefinition BuildEntity(PRDEntity prdEntity) {
        EntityDefinition entityDefinition = new EntityDefinition(prdEntity.getName(), 0); // TODO: will be decided by the user in runtime
        for (PRDProperty property : prdEntity.getPRDProperties().getPRDProperty()) {
            entityDefinition.addProperty(PropertyFactory.BuildProperty(property));
        }
        return entityDefinition;
    }
}
