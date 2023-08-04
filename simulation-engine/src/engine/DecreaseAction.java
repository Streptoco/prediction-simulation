package engine;

public class DecreaseAction extends Action {
    Property propertyToIncrease;
    Expression DecreaseBy;

    DecreaseAction(Entity entity, Property property, Expression increaseBy) {
        super(entity);
        this.propertyToIncrease = property;
        this.DecreaseBy = increaseBy;
    }
}
