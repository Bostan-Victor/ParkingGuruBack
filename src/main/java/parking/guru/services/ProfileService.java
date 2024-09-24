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

    @PreAuthorize("hasAuthority('POLICE')")
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }


    @PreAuthorize("hasAuthority('POLICE')")
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isPresent()) {
            profileRepository.deleteById(id);
        } else {
            throw new RuntimeException("Profile with ID " + id + " not found.");
        }
    }
}