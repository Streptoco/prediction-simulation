package testing;

import java.util.ArrayList;
import java.util.List;

public class testDTO {
    public String name;
    public int id;
    public String entityName;
    public long idNumber;
    public List<String> listOfRules;
    public List<String> listOfEntities;
    public List<String> listOfProperties;

    public testDTO()
    {
        this.name = "John Doe";
        this.id = 316510430;
        this.entityName = "GunSlinger";
        listOfRules = new ArrayList<>();
        listOfProperties = new ArrayList<>();
        listOfEntities = new ArrayList<>();
        listOfRules.add("aging");
        listOfRules.add("gun-pulling");
        listOfRules.add("smoking");
        listOfEntities.add("Jamie");
        listOfEntities.add("Arthur Morgan");
        listOfProperties.add("Amount of guns");
        listOfProperties.add("Life left");
        listOfProperties.add("Ipsum Lorem");
    }
}
