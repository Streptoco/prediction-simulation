package enginetoui.dto.basic.impl;

import engine.action.api.ActionInterface;

import java.util.ArrayList;
import java.util.List;

public class RuleDTO {
    public final String name;
    public final int tick;
    public final double probability;
    public final int numOfActions;
    public final List<ActionDTO> actionNames;

    public RuleDTO(String name, int tick, double probability, int numOfActions, List<ActionInterface> actions) {
        this.name = name;
        this.tick = tick;
        this.probability = probability;
        this.numOfActions = numOfActions;
        this.actionNames = new ArrayList<>();
        if (actions != null) {
            for(ActionInterface action : actions) {
                actionNames.add(new ActionDTO(action.getActionType() ,action.getPropertyName()));
            }
        }
    }
}
