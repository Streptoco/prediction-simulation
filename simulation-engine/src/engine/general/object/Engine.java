package engine.general.object;

import java.util.ArrayList;

public class Engine {
    private ArrayList<World> simulations; //TODO: should be map, key:value pairs. the key would be generated.

    public void addSimulation(World world) {
        simulations.add(world);
    }
}
