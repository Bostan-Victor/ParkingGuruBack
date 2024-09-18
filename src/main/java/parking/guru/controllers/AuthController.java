package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking.guru.dtos.CreateProfileInput;
import parking.guru.dtos.UpdateProfileInput;
import parking.guru.models.Profile;
import parking.guru.services.ProfileService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class AuthController {

    private final ProfileService profileService;

    // REST - Create a profile
    @PostMapping("/create")
    public ResponseEntity<Profile> createProfile(@RequestBody CreateProfileInput input) {
        Profile profile = new Profile();
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        profile.setIsVerified(input.getIsVerified());

        Profile savedProfile = profileService.saveProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

    // REST - Update a profile
    @PutMapping("/update/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody UpdateProfileInput input) {
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

        Profile updatedProfile = profileService.saveProfile(profile);
        return ResponseEntity.ok(updatedProfile);
    }

    // REST - Delete a profile
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok(true);
    }

    // REST - Get all profiles
    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        if (profiles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profiles);
    }

    // REST - Get profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Optional<Profile> profile = profileService.getProfileById(id);
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
