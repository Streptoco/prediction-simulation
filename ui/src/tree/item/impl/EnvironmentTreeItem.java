package tree.item.impl;

import enginetoui.dto.basic.impl.EnvironmentDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentTreeItem extends TreeItem<String> implements TreeItemEnabled {

    private EnvironmentDTO environmentDTO;
    private List<PropertyDTO> propertyDTOList;

    public EnvironmentTreeItem(EnvironmentDTO environmentDTO) {
        super("Environment");
        this.environmentDTO = environmentDTO;
        this.propertyDTOList = new ArrayList<>();
        for (PropertyDTO propertyDTO : environmentDTO.propertyDTOs) {
            PropertyTreeItem propertyTreeItem = new PropertyTreeItem(propertyDTO);
            propertyDTOList.add(propertyDTO);
            this.getChildren().add(propertyTreeItem);
        }
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {

    }
}
