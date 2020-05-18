package tms.util;

public class IntersectionNotFoundException extends Exception {
    public IntersectionNotFoundException() {
        super();
    }

    public IntersectionNotFoundException(String message) {
        super(message);
    }

    public IntersectionNotFoundException(String message, Throwable err) {
        super(message, err);
    }
}
