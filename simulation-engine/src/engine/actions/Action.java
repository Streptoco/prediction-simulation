package engine.actions;

// an actions "acts" on entities

import engine.Entity;

public class Action {
    Entity entity;
    ActionType type;
    public Action(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() { return this.entity; }

    protected void setActionType(ActionType type) { this.type = type; }

    public ActionType getActionType() { return this.type; }
}
