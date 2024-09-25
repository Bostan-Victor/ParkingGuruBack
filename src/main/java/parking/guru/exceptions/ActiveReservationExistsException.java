package parking.guru.exceptions;

public class ActiveReservationExistsException extends RuntimeException {
    public ActiveReservationExistsException(String message) {
        super(message);
    }
}
