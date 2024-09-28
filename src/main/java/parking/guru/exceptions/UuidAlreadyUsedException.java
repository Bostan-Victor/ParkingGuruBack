package parking.guru.exceptions;

public class UuidAlreadyUsedException extends RuntimeException {
    public UuidAlreadyUsedException(String message) {
        super(message);
    }
}
