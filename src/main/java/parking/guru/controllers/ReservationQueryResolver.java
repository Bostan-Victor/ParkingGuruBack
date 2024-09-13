package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Reservation;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationQueryResolver {

    private final ReservationService reservationService;

    // Query to get reservation history for a user
    @QueryMapping  // Annotation for GraphQL queries
    public List<Reservation> reservationHistory(Long userId) {
        return reservationService.getReservationHistory(userId);
    }

    // Query to get the active reservation for a user
    @QueryMapping
    public Reservation activeReservation(Long userId) {
        return reservationService.getActiveReservation(userId);
    }

    // Query to check reservation status by plate number
    @QueryMapping
    public Status checkReservationStatusByPlateNumber(@Argument String plateNumber) {
        Reservation reservation = reservationService.getReservationByPlateNumber(plateNumber);
        if (reservation == null) {
            return Status.NO_TICKET;
        }
        return reservation.getStatus();
    }
}
