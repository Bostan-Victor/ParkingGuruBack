package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Profile;
import parking.guru.services.ProfileService;
import parking.guru.dtos.CreateProfileInput;
import parking.guru.dtos.UpdateProfileInput;

@Controller
@RequiredArgsConstructor
public class ProfileMutationResolver {

    private final ProfileService profileService;

    // Mutation for creating a new profile
    @MutationMapping
    public Profile createProfile(@Argument CreateProfileInput input) {
        // Create a new Profile object
        Profile profile = new Profile();
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        profile.setIsVerified(input.getIsVerified());

        // Save and return the newly created profile
        return profileService.saveProfile(profile);
    }

    // Mutation for updating an existing profile
    @MutationMapping
    public Profile updateProfile(@Argument Long id, @Argument UpdateProfileInput input) {
        // Fetch the existing profile by ID
        Profile profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // Update the profile fields if present in the input
        if (input.getFirstName() != null) {
            profile.setFirstName(input.getFirstName());
        }
        if (input.getLastName() != null) {
            profile.setLastName(input.getLastName());
        }
        if (input.getIsVerified() != null) {
            profile.setIsVerified(input.getIsVerified());
        }

        // Save and return the updated profile
        return profileService.saveProfile(profile);
    }

    // Mutation for deleting a profile by ID
    @MutationMapping
    public Boolean deleteProfile(@Argument Long id) {
        // Call the deleteProfile method from the ProfileService
        profileService.deleteProfile(id);
        return true; // Return true if deletion was successful
    }
}
