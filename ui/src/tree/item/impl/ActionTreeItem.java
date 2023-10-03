package tree.item.impl;

import com.sun.org.apache.xpath.internal.operations.Mult;
import enginetoui.dto.basic.api.AbstractActionDTO;
import enginetoui.dto.basic.api.ActionDTOInterface;
import enginetoui.dto.basic.impl.CalculationActionDTO;
import enginetoui.dto.basic.impl.ConditionActionDTO;
import enginetoui.dto.basic.impl.IncreaseActionDTO;
import enginetoui.dto.basic.impl.MultipleConditionActionDTO;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import tree.item.api.TreeItemEnabled;

public class ActionTreeItem extends TreeItem<String> implements TreeItemEnabled {

    public AbstractActionDTO actionDTO;

    public ActionTreeItem(AbstractActionDTO actionDTO) {
        super(actionDTO.getType().toString());
        this.actionDTO = actionDTO;
    }

    @Override
    public void ApplyText(TextArea mainTextArea) {
        switch (actionDTO.getType()) {
            case INCREASE:
            case DECREASE:
                IncreaseActionDTO increaseDTO = (IncreaseActionDTO) actionDTO;
                mainTextArea.appendText(increaseDTO.getType().toString() + '\n');
                mainTextArea.appendText(increaseDTO.expressionUsed + '\n');
                mainTextArea.appendText(increaseDTO.property + '\n');
                break;
            case CALCULATION:
                CalculationActionDTO calculationDTO = (CalculationActionDTO) actionDTO;
                mainTextArea.appendText(calculationDTO.getType().toString() + '\n');
                mainTextArea.appendText(calculationDTO.calculationType + '\n');
                mainTextArea.appendText(calculationDTO.property + '\n');
                mainTextArea.appendText(calculationDTO.firstExpression + '\n');
                mainTextArea.appendText(calculationDTO.secondExpression + '\n');
                break;
            case CONDITION:
                ConditionActionDTO conditionActionDTO = (ConditionActionDTO) actionDTO;
                if (conditionActionDTO.numberOfConditions == 0) {
                    mainTextArea.appendText("Single condition\n");
                    mainTextArea.appendText(conditionActionDTO.value+"\n");
                    mainTextArea.appendText(conditionActionDTO.property + "\n");
                    mainTextArea.appendText(conditionActionDTO.operator + "\n");
                } else {
                    mainTextArea.appendText("Multiple condition with " + conditionActionDTO.numberOfConditions + " sub-conditions\n");
                    mainTextArea.appendText(conditionActionDTO.operator + "\n");
                }
        }
    }
}
