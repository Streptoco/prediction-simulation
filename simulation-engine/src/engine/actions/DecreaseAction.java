package engine.actions;

import engine.EntityDefinition;
import engine.actions.api.Action;
import engine.properties.Property;

public class DecreaseAction extends Action {
    Property propertyToIncrease;
    Expression DecreaseBy;

    DecreaseAction(EntityDefinition entityDefinition, Property property, Expression increaseBy) {
        super(entityDefinition);
        this.propertyToIncrease = property;
        this.DecreaseBy = increaseBy;
    }
}
