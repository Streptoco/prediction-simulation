package tree.item.impl;

import enginetoui.dto.basic.impl.PropertyDTO;
import javafx.scene.control.TreeItem;

public class PropertyTreeItem extends TreeItem<String> {

    private PropertyDTO propertyDTO;

    public PropertyTreeItem(PropertyDTO propertyDTO) {
        super(propertyDTO.getName());
        this.propertyDTO = propertyDTO;
    }
}
