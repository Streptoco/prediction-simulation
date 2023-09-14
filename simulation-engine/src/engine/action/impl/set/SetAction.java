package engine.action.impl.set;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.impl.Singularity;
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
//        System.out.println("\tPerforming the action: " + getActionType());
//        System.out.println("\t\tSetting: " + context.getInstance(this.getEntityOfTheAction()).getId() + "" +entityOfTheAction.charAt(0) +"." + propertyName + " to: " + valueExpression.getExpression());
        propertyInstance = context.getInstance(this.getEntityOfTheAction()).getPropertyByName(propertyName);
        ReturnType returnType = propertyInstance.getPropertyType();
        valueExpression.evaluateExpression(context);
        switch (returnType) {
            case INT:
                IntProperty intProperty = (IntProperty)propertyInstance;
                intProperty.setValue(((Double)valueExpression.getValue()).intValue(), context.getCurrentTick());
                break;
            case DECIMAL:
                DecimalProperty decimalProperty = (DecimalProperty) propertyInstance;
                decimalProperty.setValue((Double) valueExpression.getValue(), context.getCurrentTick());
                break;
            case BOOLEAN:
                BooleanProperty booleanProperty = (BooleanProperty) propertyInstance;
                booleanProperty.setValue(Boolean.parseBoolean(valueExpression.getValue().toString()), context.getCurrentTick());
                break;
            case STRING:
                StringProperty stringProperty = (StringProperty) propertyInstance;
                stringProperty.setValue((String)valueExpression.getValue(), context.getCurrentTick());
                break;
            default: // TODO: handle error
        }
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
