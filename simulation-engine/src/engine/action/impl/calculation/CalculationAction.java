package engine.action.impl.calculation;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.context.api.Context;
import engine.property.api.PropertyInterface;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;

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

    public CalculationAction(String propertyName, String calculationType, Expression firstArgument, Expression secondArgument) {
        super(ActionType.CALCULATION);
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
        resultProp = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        boolean isMultiply = (this.calculationType.equals(CalculationType.MULTIPLY));
        if (checkValidityOfExpressions()) {
            if (resultProp != null) {
                    switch (resultProp.getPropertyType()) {
                        case INT:
                            if(isMultiply) {
                                //((IntProperty) resultProp).setValue((int) firstArgument.getValue() * (int) secondArgument.getValue());
                                ((IntProperty) resultProp).setValue(firstArgument.getCastedNumber().intValue() * secondArgument.getCastedNumber().intValue());
                            }
                            else {
                                if (!((int) secondArgument.getValue() == 0)) {
                                    //((IntProperty) resultProp).setValue((int) firstArgument.getValue() / (int) secondArgument.getValue());
                                    ((IntProperty) resultProp).setValue(firstArgument.getCastedNumber().intValue() / secondArgument.getCastedNumber().intValue());
                                }
                            }
                            break;
                        case DECIMAL:
                            if(isMultiply) {
                                //((DecimalProperty) resultProp).setValue((double) firstArgument.getValue() * (double) secondArgument.getValue());
                                ((DecimalProperty) resultProp).setValue(firstArgument.getCastedNumber().doubleValue() *  secondArgument.getCastedNumber().doubleValue());
                            }
                            else {
                                if (!( secondArgument.getCastedNumber().doubleValue() == 0)) {
                                    //((DecimalProperty) resultProp).setValue((double) firstArgument.getValue() / (double) secondArgument.getValue());
                                    ((DecimalProperty) resultProp).setValue(firstArgument.getCastedNumber().doubleValue() /  secondArgument.getCastedNumber().doubleValue());
                                }
                            }
                            break;
                        default:
                            //TODO: handle error
                            break;

                }
            }
        }
    }

    private boolean checkValidityOfExpressions() {
        //TODO: is it possible to multiply integer by decimal? or all the 3 arguments should be from the same kind
        if (this.resultProp.getPropertyType().equals(ReturnType.DECIMAL)) {
            return ((!firstArgument.getReturnType().equals(ReturnType.INT) || !firstArgument.getReturnType().equals(ReturnType.DECIMAL)) ||
                    (!secondArgument.getReturnType().equals(ReturnType.INT) || !secondArgument.getReturnType().equals(ReturnType.DECIMAL)));
        } else {
            if (this.resultProp.getPropertyType().equals(ReturnType.INT)) {
                return firstArgument.getReturnType().equals(ReturnType.INT) && secondArgument.getReturnType().equals(ReturnType.INT);
            }
        }
        return false;
    }
}
