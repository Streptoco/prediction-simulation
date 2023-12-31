package engine.exception;

public class XMLException extends RuntimeException {
    protected String exceptionMessage;

    public XMLException(String filePath) {
        this.exceptionMessage = "Error while handling XML File" + filePath + "\n";
    }

    @Override
    public String getMessage() {
        return exceptionMessage;
    }
}
