package fr.crab.chilling.exception;

public class TooPoorException extends RuntimeException {
    public TooPoorException(String message) {
        super("Too Poor : " + message);
    }
}
