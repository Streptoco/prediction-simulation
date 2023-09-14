package uitoengine.filetransfer;

public class PropertyInitializeDTO {
    public final String propertyName;
    public final Object value;

    public PropertyInitializeDTO(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }
}
