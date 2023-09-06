package tree.item.impl;

import engine.grid.impl.Grid;
import enginetoui.dto.basic.impl.GridDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class GridTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private GridDTO gridDTO;

    public GridTreeItem(GridDTO gridDTO) {
        super("Grid");
        this.gridDTO = gridDTO;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        mainTextArea.appendText("Grid details:\n");
        mainTextArea.appendText("Number of rows: " + gridDTO.rows + "\n");
        mainTextArea.appendText("Number of cols: " + gridDTO.cols + "\n");
    }
}
