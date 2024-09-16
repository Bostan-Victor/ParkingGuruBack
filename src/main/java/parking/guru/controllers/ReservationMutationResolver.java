package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.CreateReservationInput;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReservationMutationResolver {

    private final ReservationService reservationService;
    private final UserService userService;

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
    public Reservation updateReservation(@Argument Long id, @Argument CreateReservationInput input) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());

        reservation.setStatus(Status.CHECKED);

        return reservationService.saveReservation(reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (reservation.isPresent()) {
            reservationService.deleteReservation(id);
            return true;
        } else {
            return false;
        }
    }
}
