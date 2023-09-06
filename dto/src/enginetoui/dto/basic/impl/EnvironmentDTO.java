package enginetoui.dto.basic.impl;

import engine.property.api.PropertyInterface;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentDTO {
    public List<String> envVariableNames;

    public List<PropertyDTO> propertyDTOs;

    public EnvironmentDTO(List<String> envVariableNames, List<PropertyInterface> properties) {
        this.envVariableNames = new ArrayList<>();
        this.propertyDTOs = new ArrayList<>();
        this.envVariableNames.addAll(envVariableNames);
        for (PropertyInterface property : properties) {
            propertyDTOs.add(new PropertyDTO(property.getName(),property.getPropertyType(), property.getFrom(),
                    property.getTo(), property.getRandomStatus()));
        }
    }
}
