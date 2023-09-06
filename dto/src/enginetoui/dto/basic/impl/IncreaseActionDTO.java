package enginetoui.dto.basic.impl;

import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.property.api.PropertyInterface;
import enginetoui.dto.basic.api.AbstractActionDTO;

import java.util.List;

public class IncreaseActionDTO extends AbstractActionDTO {

    public String property = "";
    public String expressionUsed;

    public IncreaseActionDTO(ActionType type, String property, String expressionUsed) {
        super(type);
        this.property = property;
        this.expressionUsed = expressionUsed;
    }
}
