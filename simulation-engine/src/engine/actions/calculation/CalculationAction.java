package engine.actions.calculation;

import engine.Entity;
import engine.actions.Action;
import engine.properties.DecimalProperty;
import engine.properties.IntProperty;
import engine.properties.Property;

// TODO: check if given property is indeed decimal/integer

public class CalculationAction extends Action {

    Property resultProp;
    Object firstArgument;
    Object secondArgument;
    public CalculationAction(Entity entity, String propertyName) {
        super(entity);
        resultProp = entity.getPropertyByName(propertyName);
        if (resultProp.getClass().equals(IntProperty.class)) {

        }
        else if (resultProp.getClass().equals(DecimalProperty.class)) {

        }
        else {
            // TODO: handle error.
        }
    }


}
