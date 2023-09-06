package tree.item.impl;

import enginetoui.dto.basic.impl.TerminationDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class TerminationTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private TerminationDTO termination;

    public TerminationTreeItem(TerminationDTO termination) {
        super("Termination");
        this.termination = termination;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        if (termination.isUserInteractive) {
            mainTextArea.appendText("The termination is to be determined by user interaction");
        } else {
          mainTextArea.appendText("Termination details:\n");
          mainTextArea.appendText("Simulation will run " + termination.howManySecondToRun
                  + " seconds, or " + termination.ticks + " ticks.\n");
        }
    }
}
