package tree.item.impl;

import enginetoui.dto.basic.impl.PropertyDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class PropertyTreeItem extends TreeItem<String> implements TreeItemEnabled {

    private PropertyDTO propertyDTO;

    public PropertyTreeItem(PropertyDTO propertyDTO) {
        super(propertyDTO.getName());
        this.propertyDTO = propertyDTO;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        mainTextArea.appendText("Property details:\n");
        mainTextArea.appendText("Property name: \"" + propertyDTO.getName() + "\"\n");
        mainTextArea.appendText("Range: From " + propertyDTO.from + ", To: " + propertyDTO.to + "\n");
        if (propertyDTO.isRandomlyGenerated) {
            mainTextArea.appendText("The property is randomly generated\n");
        } else {
            mainTextArea.appendText("The property wasn't randomly generated\n");
        }
    }
}
