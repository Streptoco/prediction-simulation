package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

public class StringProperty extends AbstractProperty {
    private String value;

    //TODO:
    // When randomly initialize can only have the following:
    // 1. the letters: a-z & A-Z
    // 2. 0-9
    // 3. "(" ")" "." "-" "_" "," "!" "?"
    // 4. space
    // need to randomly select the length of the string: 1 - 50 letters

    public StringProperty(String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        //TODO: Why the name is the value?
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, ReturnType.STRING);
        this.value = name;
    }

    public Object getValue() { return this.value; }

    // TODO: a way to change it maybe through interface.

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        return null;
    }

    public void setValue(String newVal) {
        this.value = newVal;
    }
}
