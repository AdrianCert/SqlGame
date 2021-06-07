package ro.uaic.info.HttpMessage;

/**
 * Signals that an NotFoundException has occurred. This
 * class is the general class of exceptions produced by failed or
 * not found resources
 *
 * @author  Adrian-Valetin Panaintescu
 * @since   1.0
 */
public class HttpMessageNotFoundException extends Exception {
    /**
     * Error message
     */
    private String message;

    /**
     * Constructor for class exception
     */
    public HttpMessageNotFoundException() {
        super();
    }

    /**
     * Constructor for class exception
     * @param message The message
     */
    public HttpMessageNotFoundException(String message) {
        super();
        setMessage(message);
    }

    /**
     * Getter for message
     * @return The message value
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Setter for message
     * @param message Th message value
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
