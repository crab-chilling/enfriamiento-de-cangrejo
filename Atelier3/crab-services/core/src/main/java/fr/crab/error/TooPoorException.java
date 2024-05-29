package fr.crab.error;

public class TooPoorException extends RuntimeException {
    public TooPoorException(String message) {
        super("Too Poor : " + message);
    }
}
