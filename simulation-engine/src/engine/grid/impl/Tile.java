package engine.grid.impl;

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
}
