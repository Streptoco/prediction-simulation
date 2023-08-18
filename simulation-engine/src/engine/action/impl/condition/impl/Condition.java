package engine.action.impl.condition.impl;

import engine.context.api.Context;

public interface Condition {
    public boolean evaluate(Context context);
}
