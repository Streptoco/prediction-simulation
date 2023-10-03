package tree.item.impl;

import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.RuleDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.scene.control.TreeItem;

public class WorldTreeItem extends TreeItem<String> {
    private WorldDTO worldDTO;

    public WorldTreeItem(WorldDTO worldDTO) {
        super("World:  " + worldDTO.worldName);
        this.worldDTO = worldDTO;

        RuleTreeFatherItem ruleTreeFatherItem = new RuleTreeFatherItem();

        EntitiesTreeFatherItem entitiesTreeFatherItem = new EntitiesTreeFatherItem();

        TerminationTreeItem terminationTreeItem = new TerminationTreeItem(worldDTO.termination);


        EnvironmentTreeItem environmentTreeItem = new EnvironmentTreeItem(worldDTO.environment);

        GridTreeItem gridTreeItem = new GridTreeItem(worldDTO.gridDTO);

        this.getChildren().setAll(ruleTreeFatherItem, entitiesTreeFatherItem, environmentTreeItem, terminationTreeItem, gridTreeItem);

        for (EntityDTO entity : worldDTO.getEntities()) {
            EntityTreeItem newEntity = new EntityTreeItem(entity);
            entitiesTreeFatherItem.getChildren().add(newEntity);
        }

        for (RuleDTO ruleDTO : worldDTO.getRules()) {
            RuleTreeItem newRule = new RuleTreeItem(ruleDTO);
            ruleTreeFatherItem.getChildren().add(newRule);
        }


    }
}
