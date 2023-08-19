package engine.exception;

public class XMLFileException extends XMLException{
    public XMLFileException(String filePath) {
        super(filePath);
        this.exceptionMessage += "The file is not a XML file";
    }
}
