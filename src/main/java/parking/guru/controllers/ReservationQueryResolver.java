package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.dtos.ActiveReservationResponse;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReservationQueryResolver {

    private final ReservationService reservationService;
    private final UserService userService;

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
    public ActiveReservationResponse getActiveReservation() {
        // Retrieve the current user from the security context
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.validateAndGetUserByEmail(userEmail);

        // Find the active reservation where endDateTime is null
        Optional<Reservation> activeReservation = Optional.ofNullable(reservationService.getActiveReservation(user.getId()));

        if (activeReservation.isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            Duration elapsedTime = Duration.between(activeReservation.get().getStartDateTime(), now);

            return new ActiveReservationResponse(true, "Active reservation found", activeReservation.get(), elapsedTime.toSeconds());
        } else {
            return new ActiveReservationResponse(false, "No active reservation found for this user", null, 0L);
        }
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