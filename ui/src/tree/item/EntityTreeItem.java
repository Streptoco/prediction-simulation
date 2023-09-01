package tree.item;

import engine.entity.impl.EntityDefinition;
import enginetoui.dto.basic.impl.EntityDTO;
import javafx.scene.control.TreeItem;

public class EntityTreeItem extends TreeItem<String> {
    private EntityDTO entity;

    public EntityTreeItem(EntityDTO entity) {
        super(entity.getName());
        this.entity = entity;
    }
}
