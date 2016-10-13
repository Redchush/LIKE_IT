package by.epam.like_it.exception.command;


public class CommandException extends Exception{

    private static final long serialVersionUID = 1L;

    public CommandException() {
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
