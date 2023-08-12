package engine.actions.impl.condition.api;

public class PropertyExpressionEvaluation {
    private boolean isEqual;
    private boolean isGreater;

    public PropertyExpressionEvaluation(boolean equality, boolean bigger)
    {
        this.isEqual = equality;
        this.isGreater = bigger;

    }

    public boolean isEqual() {
        return this.isEqual;
    }

    public boolean isGreater() {
        return this.isGreater;
    }
}
