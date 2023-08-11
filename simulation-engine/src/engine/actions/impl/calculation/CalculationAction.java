package engine.actions.impl.calculation;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionType;
import engine.actions.expression.Expression;
import engine.entity.impl.EntityDefinition;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

// TODO: check if given property is indeed decimal/integer

enum CalculationType {
    MULTIPLY,
    DIVIDE
}

public class CalculationAction extends AbstractAction {

    PropertyInterface resultProp;
    Expression firstArgument;
    Expression secondArgument;
    public CalculationAction(EntityDefinition entityDefinition, String propertyName, String calculationType) {
        super(ActionType.CALCULATION, entityDefinition);
        resultProp = entityDefinition.getPropertyByName(propertyName);
        if (resultProp.getClass().equals(IntProperty.class)) {

        }
        else if (resultProp.getClass().equals(DecimalProperty.class)) {

        }
        else {
            // TODO: handle error.
        }
    }
    @Override
    public void invoke() {

    }


}
