package by.epam.like_it.exception.command;


/**
 * Indicates that user send invalid data and there is no need to ask service layer handle action.
 * Uses in hierarchy handling the command : the child signalise the parent to stop default action and themself define
 * the behaviour
 */
public class InvalidDataCommandException extends CommandException{

    private String newPath;

    public InvalidDataCommandException() {
    }

    public InvalidDataCommandException(String message, String newPath) {
        super(message);
        this.newPath = newPath;
    }

    public InvalidDataCommandException(String message) {
        super(message);
    }

    public InvalidDataCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }
}
