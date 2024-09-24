package parking.guru.dtos;

import parking.guru.models.Reservation;

public class ActiveReservationResponse {
    private boolean success;
    private String message;
    private Reservation reservation;
    private long elapsedTime;

    // Constructor
    public ActiveReservationResponse(boolean success, String message, Reservation reservation, long elapsedTime) {
        this.success = success;
        this.message = message;
        this.reservation = reservation;
        this.elapsedTime = elapsedTime;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
