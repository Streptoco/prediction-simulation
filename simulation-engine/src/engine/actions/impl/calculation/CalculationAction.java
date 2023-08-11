package engine.actions.impl.calculation;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionType;
import engine.actions.expression.Expression;
import engine.actions.expression.ReturnType;
import engine.context.api.Context;
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

    private PropertyInterface resultProp;
    private Expression firstArgument;
    private Expression secondArgument;
    private String propertyName;
    private CalculationType calculationType;

    public CalculationAction(EntityDefinition entityDefinition, String propertyName, String calculationType, Expression firstArgument, Expression secondArgument) {
        super(ActionType.CALCULATION, entityDefinition);
        this.propertyName = propertyName;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        if (calculationType.equalsIgnoreCase("multiply")) {
            this.calculationType = CalculationType.MULTIPLY;
        }
        else if (calculationType.equalsIgnoreCase("divide")) {
            this.calculationType = CalculationType.DIVIDE;
        }
        else {
            // TODO: handle exception: not divide or multiply.
        }
    }
    @Override
    public void invoke(Context context) {
        // function to check validity
        if (checkValidityOfExpressions()) {
            resultProp = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
            if (resultProp != null) {
                if (this.calculationType.equals(CalculationType.MULTIPLY)) {
                    resultProp.setValue =
                }
            }
        }
    }

    private boolean checkValidityOfExpressions() {
        return ((!firstArgument.getReturnType().equals(ReturnType.INT) || !firstArgument.getReturnType().equals(ReturnType.DECIMAL)) ||
                (!secondArgument.getReturnType().equals(ReturnType.INT) || !secondArgument.getReturnType().equals(ReturnType.DECIMAL)));
    }


}
