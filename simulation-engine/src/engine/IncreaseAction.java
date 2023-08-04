package engine;

// TODO: this and decrease are very similar, might as well make them inherit from a common ancestor

public class IncreaseAction extends Action {
    Property propertyToIncrease;
    Expression IncreaseBy;

    IncreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        this.propertyToIncrease = property;
        this.IncreaseBy = increaseBy;
    }
}
