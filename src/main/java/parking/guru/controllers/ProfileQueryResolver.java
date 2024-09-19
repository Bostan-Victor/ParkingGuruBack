package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.models.Profile;
import parking.guru.services.ProfileService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileQueryResolver {

    private final ProfileService profileService;

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
}
