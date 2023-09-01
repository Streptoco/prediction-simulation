package engine.grid.impl;

import engine.entity.impl.EntityInstance;

public class Sack {

    private EntityInstance entity;
    private boolean moved;
    // TODO: add metrics for proximity (probably should be in the tile though)
    public Sack(EntityInstance entity) {
        this.entity = entity;
        this.moved = false;
    }

    public EntityInstance getEntity() {
        return entity;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
