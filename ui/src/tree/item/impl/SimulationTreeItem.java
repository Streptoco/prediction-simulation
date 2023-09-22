package tree.item.impl;

import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class SimulationTreeItem extends TreeItem<String> implements TreeItemEnabled {
    private SimulationStatusDTO simulationStatusDTO;
    public SimulationTreeItem(SimulationStatusDTO simulationStatusDTO) {
        super("Simulation: " + String.valueOf(simulationStatusDTO.simID));
        this.simulationStatusDTO = simulationStatusDTO;
    }

    public SimulationTreeItem() {

    }

    public SimulationStatusDTO getSimulationStatusDto() {return simulationStatusDTO;}
    @Override
    public void ApplyText(TextArea mainTextArea) {

    }


}
