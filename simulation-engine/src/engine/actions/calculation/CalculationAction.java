package engine.actions.calculation;

import engine.EntityDefinition;
import engine.actions.api.Action;
import engine.properties.DecimalProperty;
import engine.properties.IntProperty;
import engine.properties.Property;

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
