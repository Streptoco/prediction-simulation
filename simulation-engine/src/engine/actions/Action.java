package engine.actions;

// an actions "acts" on entities

import engine.Entity;

public class Action {
    Entity entity;
    public Action(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() { return this.entity; }
}
