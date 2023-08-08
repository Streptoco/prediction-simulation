package engine.actions;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor
// TODO: error handling

import engine.properties.DecimalProperty;
import engine.Entity;
import engine.properties.IntProperty;
import engine.properties.Property;

public class IncreaseAction extends Action implements ActionInterface {
    Property propertyToIncrease;
    Expression increaseBy;

    public IncreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        super.setActionType(ActionType.INCREASE);
        this.propertyToIncrease = property;
        this.increaseBy = increaseBy;
    }

    public void invokeAction() {
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
