package enginetoui.dto.basic.api;

import engine.action.api.ActionType;

public class AbstractActionDTO implements ActionDTOInterface{

    private final ActionType type;


    public AbstractActionDTO(ActionType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ActionType getType() {
        return type;
    }
}
