package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.dtos.UserProfileResponse;
import parking.guru.models.Profile;
import parking.guru.models.User;
import parking.guru.services.ProfileService;
import parking.guru.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileQueryResolver {

    private final ProfileService profileService;
    private final UserService userService;

    @QueryMapping
    public List<Profile> allProfiles() {
        return profileService.getAllProfiles();
    }

    @QueryMapping
    public Profile profileById(@Argument Long id) {
        SecurityContextHolder.getContext();
        return profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with id: " + id));
    }

    @QueryMapping
    public UserProfileResponse getUserProfile() {
        // Get the currently authenticated user from the security context
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.validateAndGetUserByEmail(userEmail);

        // Return the user's profile information
        return new UserProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
