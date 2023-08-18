import engine.property.api.PropertyInterface;

import java.util.ArrayList;
import java.util.List;

public class EntityDTO {
    public final String name;
    public final int population;
    public final List<PropertyDTO> propertyList;

    public EntityDTO(String name, int population, List<PropertyInterface> properties) {
        this.name = name;
        this.population = population;
        this.propertyList = new ArrayList<>();
        for(PropertyInterface property : properties) {
            this.propertyList.add(new PropertyDTO(property.getName(), property.getPropertyType(), property.getFrom(), property.getTo(), property.getRandomStatus()));
        }
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public List<PropertyDTO> getPropertyList() {
        return propertyList;
    }
}
