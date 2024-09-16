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

    @QueryMapping
    public List<Reservation> reservationHistory(Long userId) {
        return reservationService.getReservationHistory(userId);
    }

    @QueryMapping
    public Reservation activeReservation(Long userId) {
        return reservationService.getActiveReservation(userId);
    }

    @QueryMapping
    public Status checkReservationStatusByPlateNumber(@Argument String plateNumber) {
        Reservation reservation = reservationService.getReservationByPlateNumber(plateNumber);
        if (reservation == null) {
            return Status.NO_TICKET;
        }
        return reservation.getStatus();
    }
}
