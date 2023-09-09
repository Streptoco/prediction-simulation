package uitoengine.filetransfer;

public class EntityAmountDTO {
    public final String entityName;
    public final int amountInPopulation;

    public EntityAmountDTO(String entityName, int amountInPopulation) {
        this.entityName = entityName;
        this.amountInPopulation = amountInPopulation;
    }

    public EntityAmountDTO(String entityName) {
        this.entityName = entityName;
        this.amountInPopulation = 0;
    }
}
