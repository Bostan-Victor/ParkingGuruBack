package parking.guru.services;

import org.springframework.stereotype.Service;
import parking.guru.models.Profile;
import parking.guru.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, String firstName, String lastName, Boolean isVerified) {
        Optional<Profile> existingProfile = profileRepository.findById(id);
        if (existingProfile.isPresent()) {
            Profile profile = existingProfile.get();
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setIsVerified(isVerified);
            return profileRepository.save(profile);
        }
        return null; // or throw an exception if preferred
    }

    public Boolean deleteProfile(Long id) {
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
