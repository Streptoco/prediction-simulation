package engine.action.impl.increasedecrease;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor
// TODO: error handling

import engine.action.expression.Expression;
import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.action.expression.ReturnType;
import engine.context.api.Context;
import engine.exception.XMLException;
import engine.property.api.PropertyInterface;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;

public class IncreaseDecreaseAction extends AbstractAction {
    PropertyInterface propertyToIncrease;
    Expression increaseBy;
    IncreaseDecrease increaseDecrease;
    String propertyName;

    public IncreaseDecreaseAction(String propertyName, Expression increaseBy, String type, String actionEntity) {
        super(ActionType.INCREASE, actionEntity);
        this.propertyName = propertyName;
        this.increaseBy = increaseBy;
        if (type.equalsIgnoreCase("increase")) {
            this.increaseDecrease = IncreaseDecrease.INCREASE;
        }
        else if (type.equalsIgnoreCase("decrease")) {
            this.increaseDecrease = IncreaseDecrease.DECREASE;
        }
        else {
            // TODO: handle exception
        }
    }

    public void invoke(Context context) {
        propertyToIncrease = context.getInstance(this.getEntityOfTheAction()).getPropertyByName(propertyName);
        this.increaseBy.evaluateExpression(context);
        if (this.increaseDecrease.equals(IncreaseDecrease.INCREASE)) {
            if (propertyToIncrease.getPropertyType().equals(ReturnType.INT)) {
                IntProperty intProperty = (IntProperty)propertyToIncrease;
                intProperty.increaseValue(((Double)increaseBy.getValue()).intValue(), context.getCurrentTick());
            }
            else if (propertyToIncrease.getPropertyType().equals(ReturnType.DECIMAL)) {
                DecimalProperty decimalProperty = (DecimalProperty)propertyToIncrease;
                decimalProperty.increaseValue((Double) increaseBy.getValue(), context.getCurrentTick());
            }
            else {
                //TODO: handle exception.
            }
        }
        else {
            if (propertyToIncrease.getPropertyType().equals(ReturnType.INT)) {
                IntProperty intProperty = (IntProperty)propertyToIncrease;
                //intProperty.decreaseValue((int)increaseBy.getValue());
                intProperty.decreaseValue(((Double)increaseBy.getValue()).intValue(), context.getCurrentTick());
            }
            else if (propertyToIncrease.getPropertyType().equals(ReturnType.DECIMAL)) {
                DecimalProperty decimalProperty = (DecimalProperty)propertyToIncrease;
                //decimalProperty.decreaseValue((double)increaseBy.getValue());
                decimalProperty.decreaseValue((Double)increaseBy.getValue(), context.getCurrentTick());
            }
            else {
                //TODO: handle exception.
            }
        }
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    public String getExpression() {return this.increaseBy.getName();}
}
