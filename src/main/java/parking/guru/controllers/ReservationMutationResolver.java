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

@Controller
@RequiredArgsConstructor
public class ReservationMutationResolver {

    private final ReservationService reservationService;
    private final UserService userService;

    @MutationMapping
    public Reservation createReservation(@Argument CreateReservationInput input) {
        // Find the user by phone number (or create a new user if necessary)
        User user = userService.findByPhoneNumber(input.getPhoneNumber());

        // Create a new reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());

        // Save the reservation
        return reservationService.saveReservation(reservation);
    }
}

