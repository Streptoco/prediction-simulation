package simulation.dto;

import uitoengine.filetransfer.EntityAmountDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulationsDTO {
    private final Map<String, Integer> entities;


    public PopulationsDTO(List<EntityAmountDTO> entitiesAmount) {
        this.entities = new HashMap<>();
        entitiesAmount.forEach(currentEntity -> this.entities.put(currentEntity.entityName, currentEntity.amountInPopulation));

    }

    public Map<String, Integer> getEntities() {
        return entities;
    }
}