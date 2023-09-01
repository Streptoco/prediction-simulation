package engine.action.impl.set;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.context.api.Context;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

public class SetAction extends AbstractAction {
    private PropertyInterface propertyInstance;
    private String propertyName;
    private Expression valueExpression;
    public SetAction(String propertyName, Expression valueExpression, String actionEntity) {
        super(ActionType.SET, actionEntity);
        this.propertyName = propertyName;
        this.valueExpression = valueExpression;
    }

    @Override
    public void invoke(Context context) {
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        ReturnType returnType = propertyInstance.getPropertyType();
        valueExpression.evaluateExpression(context);
        switch (returnType) {
            case INT:
                IntProperty intProperty = (IntProperty)propertyInstance;
                intProperty.setValue(valueExpression.getCastedNumber().intValue());
                break;
            case DECIMAL:
                DecimalProperty decimalProperty = (DecimalProperty) propertyInstance;
                decimalProperty.setValue(valueExpression.getCastedNumber().doubleValue());
                break;
            case BOOLEAN:
                BooleanProperty booleanProperty = (BooleanProperty) propertyInstance;
                booleanProperty.setValue((boolean)valueExpression.getValue());
                break;
            case STRING:
                StringProperty stringProperty = (StringProperty) propertyInstance;
                stringProperty.setValue((String)valueExpression.getValue());
                break;
            default: // TODO: handle error
        }
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
