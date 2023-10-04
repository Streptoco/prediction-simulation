package enginetoui.dto.basic.impl;

import engine.action.api.ActionType;
import enginetoui.dto.basic.api.AbstractActionDTO;

public class ProximityActionDTO extends AbstractActionDTO {

    public String sourceEntity;
    public String targetEntity;
    public String depth;

    //TODO:
    // need to add the actions of proximity action and for conditions

    public ProximityActionDTO(ActionType type, String sourceEntity, String targetEntity, String depth) {
        super(type);
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.depth = depth;
    }
}
