package tree.item;

import enginetoui.dto.basic.impl.TerminationDTO;
import javafx.scene.control.TreeItem;

public class TerminationTreeItem extends TreeItem<String> {
    private TerminationDTO termination;

    public TerminationTreeItem(TerminationDTO termination) {
        super("Termination");
        this.termination = termination;
    }
}
