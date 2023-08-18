package basic.dto;

import engine.action.api.ActionInterface;

import java.util.ArrayList;
import java.util.List;

public class RuleDTO {
    public final String name;
    public final int tick;
    public final double probability;
    public final int numOfActions;
    public final List<String> actionNames;

    public RuleDTO(String name, int tick, double probabilty, int numOfActions, List<ActionInterface> actions) {
        this.name = name;
        this.tick = tick;
        this.probability = probabilty;
        this.numOfActions = numOfActions;
        this.actionNames = new ArrayList<>();
        for(ActionInterface action : actions) {
            actionNames.add("WTF?!"); //TODO: WTF IS ACTION NAME??
        }
    }
}
