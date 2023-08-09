package engine.actions.api;

// an actions "acts" on entities

import engine.EntityDefinition;

public class Action {
    EntityDefinition entityDefinition;
    ActionType type;
    public Action(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;
    }

    public EntityDefinition getEntity() { return this.entityDefinition; }

    protected void setActionType(ActionType type) { this.type = type; }

    public ActionType getActionType() { return this.type; }
}
