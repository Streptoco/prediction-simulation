package engine;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor

public class IncreaseAction extends Action {
    Property propertyToIncrease;
    Expression increaseBy;

    public IncreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        this.propertyToIncrease = property;
        this.increaseBy = increaseBy;
    }

    public void invokeAction() {
        System.out.println(increaseBy.toString());
        if (increaseBy.type.equals(Type.FREE)) {
            int numberToConserve;
            try {
                if (propertyToIncrease.getClass().equals(IntProperty.class)) {
                    IntProperty superProperty = (IntProperty) propertyToIncrease;
                    try {
                        numberToConserve = Integer.parseInt(increaseBy.name);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("the value to be put in string: %s, the value to be converted: %d\n", increaseBy.name, numberToConserve);
                    superProperty.setValue(numberToConserve);
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
}
