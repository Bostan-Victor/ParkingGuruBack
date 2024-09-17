package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Reservation;
import parking.guru.services.ReservationService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReservationQueryResolver {

    private final ReservationService reservationService;

    @QueryMapping
    public List<Reservation> reservationHistory(@Argument Long userId) {
        return reservationService.getReservationHistory(userId);
    }

    @QueryMapping
    public Reservation activeReservation(@Argument Long userId) {
        return reservationService.getActiveReservation(userId);
    }

    @QueryMapping
    public Reservation reservationById(@Argument Long id) {
        return reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @QueryMapping
    public List<Reservation> allReservations() {
        return reservationService.getAllReservations();
    }
}
