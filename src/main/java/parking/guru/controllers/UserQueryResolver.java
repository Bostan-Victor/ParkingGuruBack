package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.dtos.IsUserPoliceResponse;
import parking.guru.dtos.UserProfileResponse;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Role;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;
import parking.guru.exceptions.CustomGraphQLException;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    private final UserService userService;
    private final ReservationService reservationService;

    @QueryMapping
    public User userById(@Argument Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new CustomGraphQLException("User not found with id: " + id));
    }

    @QueryMapping
    public User userByEmail(@Argument String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new CustomGraphQLException("User not found with email: " + email));
    }

    @QueryMapping
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public UserProfileResponse getUserProfile() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.validateAndGetUserByEmail(userEmail);

        return new UserProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    @QueryMapping
    public IsUserPoliceResponse isUserPolice() {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User currentUser = userService.validateAndGetUserByEmail(userEmail);

        // Check if the user has the POLICE role
        if (currentUser.getRole() == Role.POLICE) {
            return new IsUserPoliceResponse(true, null); // Return true if the user is POLICE, no reservation
        }

        // If the user has the USER role, find the active reservation (endDateTime is null)
        if (currentUser.getRole() == Role.USER) {
            Optional<Reservation> activeReservation = Optional.ofNullable(reservationService.getActiveReservation((long) currentUser.getId()));

            // Return response with the active reservation or null if not found
            return new IsUserPoliceResponse(false, activeReservation.orElse(null));
        }

        // If neither role is found (which shouldn't happen), return a default response
        return new IsUserPoliceResponse(false, null);
    }
}
