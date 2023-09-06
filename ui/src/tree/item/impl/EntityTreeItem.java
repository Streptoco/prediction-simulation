package tree.item.impl;

import engine.entity.impl.EntityDefinition;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class EntityTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private final EntityDTO entity;

    public EntityTreeItem(EntityDTO entity) {
        super(entity.getName());
        this.entity = entity;
    }

    public EntityDTO getEntity() {
        return entity;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        int counter = 1;
        mainTextArea.appendText("Entity name: \"" + entity.name + "\"\n");
        mainTextArea.appendText("Amount of instances: " + entity.getPopulation() + "\n");
        mainTextArea.appendText("List of properties: \n");
        for (PropertyDTO propertyDTO : entity.propertyList) {
            mainTextArea.appendText(counter++ + ". ");
            mainTextArea.appendText(propertyDTO.getName() + "\n");
            mainTextArea.appendText("Range: From " + propertyDTO.from + ", To: " + propertyDTO.to + "\n");
            if (propertyDTO.isRandomlyGenerated) {
                mainTextArea.appendText("The property is randomly generated\n");
            } else {
                mainTextArea.appendText("The property wasn't randomly generated\n");
            }
        }
    }
}
