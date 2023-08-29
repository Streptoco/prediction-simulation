package engine.action.impl.proximity;

import engine.action.api.AbstractAction;
import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class ProximityAction extends AbstractAction {
    private String sourceEntity;
    private String targetEntity;
    private Expression depth;

    private List<ActionInterface> actionList = new ArrayList<>();

    public ProximityAction(ActionType actionType, String sourceEntity, String targetEntity, Expression depth, List<ActionInterface> actionList) {
        super(actionType);
        this.actionList = actionList;
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
