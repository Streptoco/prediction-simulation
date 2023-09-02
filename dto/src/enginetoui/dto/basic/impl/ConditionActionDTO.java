package enginetoui.dto.basic.impl;

import engine.action.api.ActionType;
import enginetoui.dto.basic.api.AbstractActionDTO;

public class ConditionActionDTO extends AbstractActionDTO {
    public int numberOfConditions;

    public String operator;

    public String value;

    public String property;

    public ConditionActionDTO(ActionType type, int numberOfConditions, String operator, String property, String value) {
        super(type);
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.numberOfConditions = numberOfConditions;
    }
}
