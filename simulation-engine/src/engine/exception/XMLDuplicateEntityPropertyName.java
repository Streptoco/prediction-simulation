package engine.exception;

public class XMLDuplicateEntityPropertyName  extends XMLException {
    public XMLDuplicateEntityPropertyName(String filePath, String entityName, String propertyName) {
        super(filePath);
        this.exceptionMessage += "There is a duplication in \"" + entityName + "\" properties, with \"" + propertyName + "\" property";
    }
}
