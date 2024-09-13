package parking.guru.services;

import org.springframework.stereotype.Service;
import parking.guru.repositories.ProfileRepository;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
}
