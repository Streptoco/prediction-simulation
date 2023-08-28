package engine.worldbuilder.factory;

import engine.grid.impl.Grid;
import engine.worldbuilder.prdobjects.PRDWorld;

public class GridFactory {
    public static Grid BuildGrid(PRDWorld.PRDGrid prdGrid) {
        return new Grid(prdGrid.getRows(), prdGrid.getColumns());
    }
}
