package tree.item.impl;

import enginetoui.dto.basic.impl.RuleDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class RuleTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private RuleDTO rule;

    public RuleTreeItem(RuleDTO rule) {
        super(rule.name);
        this.rule = rule;
    }

    public RuleDTO getRuleDTO() {
        return rule;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        mainTextArea.appendText("Rule name: \"" + rule.name + "\"\n");
        mainTextArea.appendText(String.valueOf("Number of ticks: " + rule.tick + "\n"));
        mainTextArea.appendText(String.valueOf("Chance of happening: " + rule.probability + "\n"));
    }
}
