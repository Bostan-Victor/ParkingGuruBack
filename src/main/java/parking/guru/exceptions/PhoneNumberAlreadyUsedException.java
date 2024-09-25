package parking.guru.exceptions;

public class PhoneNumberAlreadyUsedException extends RuntimeException {
    public PhoneNumberAlreadyUsedException(String message) {
        super(message);
    }
}
