package cpe.asi.cardforge.error;

public class AlreadyExistingException extends RuntimeException {
    public AlreadyExistingException(String message) {
        super(message);
    }
}
