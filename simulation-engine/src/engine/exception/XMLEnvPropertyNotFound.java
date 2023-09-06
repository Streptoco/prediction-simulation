package engine.exception;

public class XMLEnvPropertyNotFound extends XMLException {
    public XMLEnvPropertyNotFound(String filePath, String envPropertyName) {
        super(filePath);
        this.exceptionMessage += "Couldn't find env variable named \"" + envPropertyName + "\"";
    }
}
