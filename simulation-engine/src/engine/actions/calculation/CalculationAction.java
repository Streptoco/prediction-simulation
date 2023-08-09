package engine.actions.calculation;

import engine.entity.EntityDefinition;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

// TODO: check if given property is indeed decimal/integer

public class CalculationAction extends Action {

    Property resultProp;
    Object firstArgument;
    Object secondArgument;
    public CalculationAction(EntityDefinition entityDefinition, String propertyName) {
        super(entityDefinition);
        resultProp = entityDefinition.getPropertyByName(propertyName);
        if (resultProp.getClass().equals(IntProperty.class)) {

        }
        else if (resultProp.getClass().equals(DecimalProperty.class)) {

        }
        else {
            // TODO: handle error.
        }
    }


}
