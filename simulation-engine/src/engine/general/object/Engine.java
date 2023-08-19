package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.xml.XmlReader;
import enginetoui.dto.basic.EntityDTO;
import enginetoui.dto.basic.RuleDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Engine {
    private Map<Integer, World> simulations; //TODO: should be map, key:value pairs. the key would be generated.
    private int serialNumber = 1;
    private XmlReader reader;

    public Engine() {
        simulations = new HashMap<>();
        reader = new XmlReader();
    }

    public void addSimulation(String filePath) {
        simulations.put(serialNumber++, reader.ReadXML(filePath));
    }

    public List<EntityDTO> GetAllEntities() {
        World currentWorld = simulations.get(serialNumber);
        List<EntityDTO> resultList = new ArrayList<>();
        for(EntityDefinition entity : currentWorld.GetEntities()) {
           resultList.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        return resultList;
    }

    public List<RuleDTO> GetAllRules() {
        World currentWorld = simulations.get(serialNumber);
        List<RuleDTO> resultList = new ArrayList<>();
        for(Rule rule : currentWorld.getRules()) {
            resultList.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        return resultList;
    }

    public int GetSimulationTotalTicks() { return simulations.get(serialNumber).GetSimulationTotalTicks();}
    public long GetSimulationTotalTime() { return simulations.get(serialNumber).GetSimulationTotalTime();}
}
