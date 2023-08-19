package enginetoui.dto.basic;

import engine.action.api.ActionType;

public class ActionDTO {
    public final ActionType type;
    public final String property;

    public ActionDTO(ActionType type, String property) {
        this.type = type;
        this.property = property;
    }
}
