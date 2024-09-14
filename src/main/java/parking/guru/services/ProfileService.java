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

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    public Optional<Boolean> isProfileVerified(Long id) {
        return profileRepository.isUserVerified(id);
    }
}
