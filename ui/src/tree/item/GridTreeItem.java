package tree.item;

import engine.grid.impl.Grid;
import enginetoui.dto.basic.impl.GridDTO;
import javafx.scene.control.TreeItem;

public class GridTreeItem extends TreeItem<String> {
    private GridDTO gridDTO;

    public GridTreeItem(GridDTO gridDTO) {
        super("Grid");
        this.gridDTO = gridDTO;
    }
}
