package engine.exception;

public class XMLDuplicateEnvPropertyName extends XMLException {
    public XMLDuplicateEnvPropertyName(String filePath, String propertyName) {
        super(filePath);
        this.exceptionMessage += "There is a duplication in \"" + propertyName + "\" environment property";
    }
}
