package by.bsu.graphmath.exception;

public class WolframException extends Exception {

    public WolframException() {
    }

    public WolframException(String message) {
        super(message);
    }

    public WolframException(String message, Throwable cause) {
        super(message, cause);
    }

    public WolframException(Throwable cause) {
        super(cause);
    }
}
