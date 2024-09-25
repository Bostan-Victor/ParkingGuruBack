package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.dtos.EndReservationResponse;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;
import parking.guru.dtos.CreateReservationInput;
import org.springframework.security.core.Authentication;
import parking.guru.dtos.UpdateReservationInput;

import java.time.Duration;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ReservationMutationResolver {

    private final ReservationService reservationService;
    private final UserService userService;
    private static final double PRICE_PER_MINUTE = 0.5;

    // Mutation for creating a reservation
    @MutationMapping
    public Reservation createReservation(@Argument CreateReservationInput input) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.validateAndGetUserByEmail(userEmail);

        // Create the reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setPlateNumber(input.getPlateNumber());
        reservation.setAddress(input.getAddress());  // Use the new address field
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());
        reservation.setEndDateTime(null);  // Ensure endDateTime is null at creation

        return reservationService.saveReservation(reservation);
    }

    @MutationMapping
    public EndReservationResponse endReservation() {
        // Get the currently authenticated user
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.validateAndGetUserByEmail(userEmail);

        // Find the active reservation (endDateTime is null)
        Reservation activeReservation = reservationService.getActiveReservation((long) user.getId());
        if (activeReservation == null) {
            throw new RuntimeException("No active reservation found for this user.");
        }

        // Set the current time as the end time
        LocalDateTime endDateTime = LocalDateTime.now();
        activeReservation.setEndDateTime(endDateTime);

        // Calculate the total price (duration in minutes * price per minute)
        Duration duration = Duration.between(activeReservation.getStartDateTime(), endDateTime);
        long minutes = duration.toMinutes();
        double totalPrice = minutes * PRICE_PER_MINUTE;

        // Save the updated reservation
        reservationService.saveReservation(activeReservation);

        // Return the details, including the calculated price
        return new EndReservationResponse(
                activeReservation.getId(),
                activeReservation.getPlateNumber(),
                activeReservation.getStartDateTime().toString(),
                endDateTime.toString(),
                totalPrice
        );
    }

//    @MutationMapping
//    public Reservation updateReservation(@Argument Long id, @Argument UpdateReservationInput input) {
//        Reservation reservation = reservationService.getReservationById(id)
//                .orElseThrow(() -> new RuntimeException("Reservation not found"));
//
//        if (input.getLatitude() != null) {
//            reservation.setLatitude(input.getLatitude());
//        }
//        if (input.getLongitude() != null) {
//            reservation.setLongitude(input.getLongitude());
//        }
//        if (input.getEndDateTime() != null) {
//            reservation.setEndDateTime(LocalDateTime.parse(input.getEndDateTime()));
//        }
//        if (input.getStatus() != null) {
//            reservation.setStatus(input.getStatus());
//        }
//
//        return reservationService.saveReservation(reservation);
//    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservationService.deleteReservation(id);
        return true;
    }
}