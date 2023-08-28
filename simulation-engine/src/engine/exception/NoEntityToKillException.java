package engine.exception;

public class NoEntityToKillException extends RuntimeException{
    protected String exceptionMessage;

    public NoEntityToKillException(String entityNameTryingToKill) {
        this.exceptionMessage = "The entity to create was not provided while trying to replace: \"" + entityNameTryingToKill + "\"";
    }
}
