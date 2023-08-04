package engine;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor

public class IncreaseAction extends Action {
    Property propertyToIncrease;
    Expression increaseBy;

    IncreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        this.propertyToIncrease = property;
        this.increaseBy = increaseBy;
    }

    public void invokeAction() {
        if (increaseBy.type.equals(Type.FREE)) {
            try {
                int additionToProperty = Integer.parseInt(increaseBy.name);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }
}
