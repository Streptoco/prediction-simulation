package tree.item;

import enginetoui.dto.basic.impl.RuleDTO;
import javafx.scene.control.TreeItem;

public class RuleTreeItem extends TreeItem<String> {
    private RuleDTO rule;

    public RuleTreeItem(RuleDTO rule) {
        super(rule.name);
        this.rule = rule;
    }
}
