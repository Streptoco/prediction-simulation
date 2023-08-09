package engine.actions.impl;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor
// TODO: error handling

import engine.actions.expression.Expression;
import engine.actions.Type;
import engine.actions.api.AbstractAction;
import engine.actions.api.ActionType;
import engine.context.Context;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.DecimalProperty;
import engine.entity.EntityDefinition;
import engine.properties.impl.IntProperty;

public class IncreaseAction extends AbstractAction {
    PropertyInterface propertyToIncrease;
    Expression increaseBy;

    public IncreaseAction(EntityDefinition entityDefinition, PropertyInterface property, Expression increaseBy) {
        super(ActionType.INCREASE,entityDefinition);
        this.propertyToIncrease = property;
        this.increaseBy = increaseBy;
    }

    public void invoke(Context context) { // why not first get the property type and then work from there. if it's increase it can't be boolean or string.
        if (increaseBy.type.equals(Type.FREE)) {
            Object numberToConserve;
            try {
                if (propertyToIncrease.getClass().equals(IntProperty.class)) {
                    IntProperty superProperty = (IntProperty) propertyToIncrease;
                    try {
                        numberToConserve = Integer.parseInt(increaseBy.name);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("the value to be put in string: %s, the value to be converted: %d and the new value" +
                            " is %d\n", increaseBy.name, numberToConserve, superProperty.getValue());
                    superProperty.increaseValue((int)numberToConserve);
                }
                else if (propertyToIncrease.getClass().equals(DecimalProperty.class)) {
                    DecimalProperty superProperty = (DecimalProperty) propertyToIncrease;
                    try {
                        numberToConserve = Double.parseDouble(increaseBy.name);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Value of %s before the increment: %.2f, value after: %.2f\n",
                            superProperty.getName(), superProperty.getValue(), superProperty.getValue() + (double)numberToConserve);
                    superProperty.increaseValue((double)numberToConserve);
                }
                else {
                    // TODO: add exception handling.
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        else if (increaseBy.type.equals(Type.FUNCTION)) {
            // TODO: fill in what happens with random and environment when called.
        }
    }
}
