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

    @MutationMapping
    public Profile createProfile(@Argument CreateProfileInput input) {
        Profile profile = new Profile();
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        profile.setIsVerified(input.getIsVerified());

        return profileService.saveProfile(profile);
    }

    @MutationMapping
    public Profile updateProfile(@Argument Long id, @Argument UpdateProfileInput input) {
        Profile profile = profileService.getProfileById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (input.getFirstName() != null) {
            profile.setFirstName(input.getFirstName());
        }
        if (input.getLastName() != null) {
            profile.setLastName(input.getLastName());
        }
        if (input.getIsVerified() != null) {
            profile.setIsVerified(input.getIsVerified());
        }

        return profileService.saveProfile(profile);
    }

    @MutationMapping
    public Boolean deleteProfile(@Argument Long id) {
        profileService.deleteProfile(id);
        return true;
    }
}
