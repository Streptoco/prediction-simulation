package enginetoui.dto.basic.impl;

import engine.action.api.ActionType;
import enginetoui.dto.basic.api.AbstractActionDTO;

public class CalculationActionDTO extends AbstractActionDTO {

    public String property;
    public String firstExpression;
    public String secondExpression;
    public String calculationType;
    public CalculationActionDTO(ActionType type, String property, String firstExpression, String secondExpression, String calculationType) {
        super(type);
        this.property = property;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.calculationType = calculationType;
    }
}
