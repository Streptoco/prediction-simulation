package engine.actions.impl;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor
// TODO: error handling

import engine.actions.expression.Expression;
import engine.actions.Type;
import engine.actions.api.AbstractAction;
import engine.actions.api.ActionType;
import engine.context.Context;
import engine.properties.api.PropertyInterface;
import engine.properties.api.PropertyType;
import engine.properties.impl.DecimalProperty;
import engine.entity.EntityDefinition;
import engine.properties.impl.IntProperty;
import sun.security.util.math.intpoly.IntegerPolynomial;

enum IncreaseDecrease {
    INCREASE,
    DECREASE
}

public class IncreaseAction extends AbstractAction {
    PropertyInterface propertyToIncrease;
    Expression increaseBy;
    IncreaseDecrease increaseDecrease;

    public IncreaseAction(EntityDefinition entityDefinition, PropertyInterface property, Expression increaseBy, String type) {
        super(ActionType.INCREASE,entityDefinition);
        this.propertyToIncrease = property;
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

    public void invoke(Context context) { // why not first get the property type and then work from there. if it's increase it can't be boolean or string.
        if (this.increaseDecrease.equals(IncreaseDecrease.INCREASE)) {
            if (propertyToIncrease.getPropertyType().equals(PropertyType.INT)) {
                IntProperty intProperty = (IntProperty)propertyToIncrease;
                intProperty.increaseValue((int)increaseBy.getValue());
            }
            else if (propertyToIncrease.getPropertyType().equals(PropertyType.DECIMAL)) {
                DecimalProperty decimalProperty = (DecimalProperty)propertyToIncrease;
                decimalProperty.increaseValue((double)increaseBy.getValue());
            }
            else {
                //TODO: handle exception.
            }
        }
        else {
            if (propertyToIncrease.getPropertyType().equals(PropertyType.INT)) {
                IntProperty intProperty = (IntProperty)propertyToIncrease;
                intProperty.decreaseValue((int)increaseBy.getValue());
            }
            else if (propertyToIncrease.getPropertyType().equals(PropertyType.DECIMAL)) {
                DecimalProperty decimalProperty = (DecimalProperty)propertyToIncrease;
                decimalProperty.decreaseValue((double)increaseBy.getValue());
            }
            else {
                //TODO: handle exception.
            }
        }
    }
}
