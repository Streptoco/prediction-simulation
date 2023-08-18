package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

public class StringProperty extends AbstractProperty {
    private String value;

    public StringProperty(String name, String value, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        //TODO: Why the name is the value?
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, ReturnType.STRING);
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    // TODO: a way to change it maybe through interface.

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        String expressionValue = (String) expression.getValue();
        PropertyExpressionEvaluation result;
        if (this.value.equals(expressionValue)) {
            result = new PropertyExpressionEvaluation(true, false);
        } else {
            result = new PropertyExpressionEvaluation(false, false);
        }
        return result;
    }

    public void setValue(String newVal) {
        this.value = newVal;
    }
}
