package tree.item.impl;

import enginetoui.dto.basic.api.AbstractActionDTO;
import enginetoui.dto.basic.api.ActionDTOInterface;
import enginetoui.dto.basic.impl.RuleDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class RuleTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private RuleDTO rule;


    public RuleTreeItem(RuleDTO rule) {
        super(rule.name);
        this.rule = rule;
        for (AbstractActionDTO actionDTO : rule.actionNames) {
            ActionTreeItem actionTreeItem = new ActionTreeItem(actionDTO);
            this.getChildren().add(actionTreeItem);
        }
    }

    public RuleDTO getRuleDTO() {
        return rule;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        mainTextArea.appendText("Rule name: \"" + rule.name + "\"\n");
        mainTextArea.appendText("Number of ticks: " + rule.tick + "\n");
        mainTextArea.appendText("Chance of happening: " + rule.probability + "\n");
    }
}
