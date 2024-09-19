package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import parking.guru.models.Reservation;
import parking.guru.services.ReservationService;

import java.util.List;

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
    @PreAuthorize("hasAuthority('POLICE')")
    public List<Reservation> allReservations() {
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public Reservation reservationByUserId(@Argument Long id){
        return reservationService.getReservationByUserId(id)
                .orElseThrow(()-> new RuntimeException("Reservation not found for this user" + id));
    }

    @QueryMapping
    public List<Reservation> allReservationByUserId(@Argument Long id){
        return reservationService.getAllReservationsByUserId(id);
    }

    @QueryMapping
    public Reservation checkReservationStatusByPlateNumber(@Argument String plateNumber){
        return reservationService.getReservationByPlateNumber(plateNumber);
    }
}