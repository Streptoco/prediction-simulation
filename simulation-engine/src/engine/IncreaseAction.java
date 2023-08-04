package engine;

public class IncreaseAction extends Action {
    Property propertyToIncrease;
    Expression IncreaseBy;

    IncreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        this.propertyToIncrease = property;
        this.IncreaseBy = increaseBy;
    }
}
