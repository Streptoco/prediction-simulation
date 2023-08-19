package engine.exception;

public class XMLDuplicateEntityPropertyName  extends XMLException {
    public XMLDuplicateEntityPropertyName(String filePath, String propertyName, String entityName) {
        super(filePath);
        this.exceptionMessage += "There is a duplication in " + entityName + " property " + propertyName;
    }
}
