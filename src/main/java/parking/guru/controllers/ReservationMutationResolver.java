package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;
import parking.guru.dtos.CreateReservationInput;
import parking.guru.dtos.UpdateReservationInput;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ReservationMutationResolver {

    private final ReservationService reservationService;
    private final UserService userService;

    // Mutation for creating a reservation
    @MutationMapping
    public Reservation createReservation(@Argument CreateReservationInput input) {
        User user = userService.findByPhoneNumber(input.getPhoneNumber());

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());

        return reservationService.saveReservation(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument UpdateReservationInput input) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (input.getLatitude() != null) {
            reservation.setLatitude(input.getLatitude());
        }
        if (input.getLongitude() != null) {
            reservation.setLongitude(input.getLongitude());
        }
        if (input.getEndDateTime() != null) {
            reservation.setEndDateTime(LocalDateTime.parse(input.getEndDateTime()));
        }
        if (input.getStatus() != null) {
            reservation.setStatus(input.getStatus());
        }

        return reservationService.saveReservation(reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservationService.deleteReservation(id);
        return true;
    }
}