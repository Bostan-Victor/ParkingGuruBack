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

    // Create a new reservation
    @MutationMapping
    public Reservation createReservation(@Argument CreateReservationInput input) {
        // Find the user by phone number or handle the case where the user might not exist
        User user = userService.findByPhoneNumber(input.getPhoneNumber());

        // Create a new reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());

        // Save and return the reservation
        return reservationService.saveReservation(reservation);
    }

    // Update an existing reservation
    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument CreateReservationInput input) {
        // Fetch the reservation by its ID
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Update reservation fields if provided in the input
        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());

        // Optionally update other fields (e.g., status)
        reservation.setStatus(Status.CHECKED); // Example status update

        // Save and return the updated reservation
        return reservationService.saveReservation(reservation);
    }

    // Delete a reservation by ID
    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        // Check if the reservation exists
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        if (reservation.isPresent()) {
            reservationService.deleteReservation(id);
            return true; // Successfully deleted
        } else {
            return false; // Reservation not found
        }
    }
}
