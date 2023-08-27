package engine.worldbuilder.factory;

import engine.entity.impl.EntityDefinition;
import engine.exception.XMLDuplicateEntityPropertyName;
import engine.exception.XMLDuplicateEnvPropertyName;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.*;

import java.util.HashSet;
import java.util.Set;

public class EntityFactory {
    public static EntityDefinition BuildEntity(PRDEntity prdEntity) {
        EntityDefinition entityDefinition = new EntityDefinition(prdEntity.getName(), 0); // TODO: will be decided by the user in runtime
        for (PRDProperty property : prdEntity.getPRDProperties().getPRDProperty()) {
            entityDefinition.addProperty(PropertyFactory.BuildProperty(property));
        }

        Set<String> duplicates = new HashSet<>();
        for (PropertyInterface property : entityDefinition.getProps()) {
            if (duplicates.contains(property.getName())) {
                throw new XMLDuplicateEntityPropertyName("", entityDefinition.getName(), property.getName());
            }
            duplicates.add(property.getName());
        }
        return entityDefinition;
    }
}
