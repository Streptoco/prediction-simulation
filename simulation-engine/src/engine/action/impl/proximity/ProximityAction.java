package engine.action.impl.proximity;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;

public class ProximityAction extends AbstractAction {
    private EntityDefinition sourceEntity;
    private EntityDefinition targetEntity;
    private int depth;

    public ProximityAction(ActionType actionType, EntityDefinition sourceEntity, EntityDefinition targetEntity, int depth) {
        super(actionType);
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.depth = depth;
    }

    @Override
    public void invoke(Context context) {

    }

    @Override
    public String getPropertyName() {
        return null;
    }
}
