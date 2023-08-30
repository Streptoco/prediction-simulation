package engine.grid.impl;

import engine.entity.impl.EntityInstance;

public class Tile {
    private boolean taken;
    private Sack currentSack;
    public Tile() {
        this.taken = false;
        this.currentSack = null;
        // TODO: c'tor
    }

    public boolean getTaken() {return this.taken;}

    public void setSack(Sack sack) {
        this.currentSack = sack;
        this.taken = true;
    }

    public Sack getSack() {
        return currentSack;
    }

    public EntityInstance getEntityInSack() { return currentSack.getEntity(); }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
