package tms.util;

/**
 * Exception thrown when a request is made for an Intersection that does
 * not exist.
 */
public class IntersectionNotFoundException extends Exception {

    /**
     * Constructs a normal IntersectionNotFoundException with no error message
     * or cause.
     */
    public IntersectionNotFoundException() {
        super();
    }

    /**
     * Constructs a IntersectionNotFoundException that contains a helpful
     * message detailing why the exception occurred.
     *
     * @param message detail message
     */
    public IntersectionNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a IntersectionNotFoundException that contains a helpful
     * message detailing why the exception occurred.
     *
     * @param message detail message
     * @param err cause of the exception
     */
    public IntersectionNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
