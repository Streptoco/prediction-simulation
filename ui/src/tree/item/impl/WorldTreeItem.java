package tree.item.impl;

import enginetoui.dto.basic.impl.WorldDTO;
import javafx.scene.control.TreeItem;

public class WorldTreeItem extends TreeItem<String> {
    private WorldDTO world;

    public WorldTreeItem(WorldDTO world) {
        super("World Number " + world.simulationId);
        this.world = world;
    }
}
