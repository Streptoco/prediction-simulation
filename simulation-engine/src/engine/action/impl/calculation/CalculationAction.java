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

public class CalculationAction extends AbstractAction {

    private PropertyInterface resultProp;
    private Expression firstArgument;
    private Expression secondArgument;
    private String propertyName;
    private CalculationType calculationType;

    public CalculationAction(String propertyName, String calculationType, Expression firstArgument, Expression secondArgument, String entityAction) {
        super(ActionType.CALCULATION, entityAction);
        this.propertyName = propertyName;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        if (calculationType.equalsIgnoreCase("multiply")) {
            this.calculationType = CalculationType.MULTIPLY;
        } else if (calculationType.equalsIgnoreCase("divide")) {
            this.calculationType = CalculationType.DIVIDE;
        } else {
            // TODO: handle exception: not divide or multiply.
        }
    }

    public String getCalculationType() {
        if (calculationType.equals(CalculationType.DIVIDE)) {
            return "divide";
        } else {
            return "multiply";
        }
    }

    public String getFirstExpression() {return firstArgument.getName();}

    public String getSecondArgument() {return secondArgument.getName();}


    @Override
    public void invoke(Context context) {
        resultProp = context.getInstance(this.getEntityOfTheAction()).getPropertyByName(propertyName);
        boolean isMultiply = (this.calculationType.equals(CalculationType.MULTIPLY));
        firstArgument.evaluateExpression(context);
        secondArgument.evaluateExpression(context);
        if (checkValidityOfExpressions()) {
            if (resultProp != null) {
                switch (resultProp.getPropertyType()) {
                    case INT:
                        if (isMultiply) {
                            //((IntProperty) resultProp).setValue((int) firstArgument.getValue() * (int) secondArgument.getValue());
                            ((IntProperty) resultProp).setValue(((Double) firstArgument.getValue()).intValue() * ((Double) secondArgument.getValue()).intValue(), context.getCurrentTick());
                        } else {
                            if (!((int) secondArgument.getValue() == 0)) {
                                //((IntProperty) resultProp).setValue((int) firstArgument.getValue() / (int) secondArgument.getValue());
                                ((IntProperty) resultProp).setValue(((Double) firstArgument.getValue()).intValue() / ((Double) secondArgument.getValue()).intValue(), context.getCurrentTick());
                            }
                        }
                        break;
                    case DECIMAL:
                        if (isMultiply) {
                            //((DecimalProperty) resultProp).setValue((double) firstArgument.getValue() * (double) secondArgument.getValue());
                            ((DecimalProperty) resultProp).setValue((Double) firstArgument.getValue() * (Double) secondArgument.getValue(), context.getCurrentTick());
                        } else {
                            if (!((Double)secondArgument.getValue() == 0)) {
                                //((DecimalProperty) resultProp).setValue((double) firstArgument.getValue() / (double) secondArgument.getValue());
                                ((DecimalProperty) resultProp).setValue((Double) firstArgument.getValue() / (Double) secondArgument.getValue(), context.getCurrentTick());
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

    public String getPropertyName() {
        return propertyName;
    }
}
