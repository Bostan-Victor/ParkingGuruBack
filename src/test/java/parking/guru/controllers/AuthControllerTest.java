package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import parking.guru.dtos.CreateProfileInput;
import parking.guru.dtos.UpdateProfileInput;
import parking.guru.models.Profile;
import parking.guru.services.ProfileService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProfile() {

        CreateProfileInput input = new CreateProfileInput();
        input.setFirstName("Renatik");
        input.setLastName("Python");
        input.setIsVerified(true);

        Profile profile = new Profile();
        profile.setFirstName("Renatik");
        profile.setLastName("Python");
        profile.setIsVerified(true);

        when(profileService.saveProfile(any(Profile.class))).thenReturn(profile);

        ResponseEntity<Profile> response = authController.createProfile(input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Renatik", response.getBody().getFirstName());
        verify(profileService, times(1)).saveProfile(any(Profile.class));
    }

    @Test
    void testUpdateProfile() {
        UpdateProfileInput input = new UpdateProfileInput();
        input.setFirstName("John");
        input.setIsVerified(false);

        Profile profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setIsVerified(true);

        when(profileService.getProfileById(eq(1L))).thenReturn(Optional.of(profile));
        when(profileService.saveProfile(any(Profile.class))).thenReturn(profile);

        ResponseEntity<Profile> response = authController.updateProfile(1L, input);

        assertEquals(200, response.getStatusCodeValue());
        verify(profileService, times(1)).getProfileById(eq(1L));
        verify(profileService, times(1)).saveProfile(any(Profile.class));
    }

    @Test
    void testDeleteProfile() {
        ResponseEntity<Boolean> response = authController.deleteProfile(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(profileService, times(1)).deleteProfile(1L);
    }

    @Test
    void testGetAllProfiles() {
        Profile profile1 = new Profile();
        profile1.setFirstName("John");
        profile1.setLastName("Doe");

        Profile profile2 = new Profile();
        profile2.setFirstName("Jane");
        profile2.setLastName("Smith");

        when(profileService.getAllProfiles()).thenReturn(Arrays.asList(profile1, profile2));

        ResponseEntity<List<Profile>> response = authController.getAllProfiles();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(profileService, times(1)).getAllProfiles();
    }

    @Test
    void testGetProfileById() {
        Profile profile = new Profile();
        profile.setFirstName("John");
        profile.setLastName("Doe");

        when(profileService.getProfileById(eq(1L))).thenReturn(Optional.of(profile));

        ResponseEntity<Profile> response = authController.getProfileById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getFirstName());
        verify(profileService, times(1)).getProfileById(eq(1L));
    }
}
