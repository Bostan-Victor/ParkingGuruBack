package parking.guru.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import parking.guru.models.Profile;
import parking.guru.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    // Fetch all profiles
    @PreAuthorize("hasAuthority('POLICE')")
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    // Fetch profile by ID
    @PreAuthorize("hasAuthority('POLICE')")
    public Optional<Profile> getProfileById(Long id) {
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profileRepository.findById(id);
    }

    // Save or update profile
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // Delete profile by ID
    public void deleteProfile(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isPresent()) {
            profileRepository.deleteById(id);
        } else {
            throw new RuntimeException("Profile with ID " + id + " not found.");
        }
    }
}
