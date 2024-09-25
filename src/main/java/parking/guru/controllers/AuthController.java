package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import parking.guru.config.security.TokenProvider;
import parking.guru.config.security.oauth2.OAuth2Provider;
import parking.guru.dtos.AuthResponse;
import parking.guru.dtos.LoginRequest;
import parking.guru.dtos.SignUpRequest;
import parking.guru.exceptions.EmailAlreadyUsedException;
import parking.guru.exceptions.PhoneNumberAlreadyUsedException;
import parking.guru.exceptions.UuidAlreadyUsedException;
import parking.guru.models.Profile;
import parking.guru.models.User;
import parking.guru.models.enums.Role;
import parking.guru.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    private static Profile getProfile(SignUpRequest signUpRequest) {
        Profile profile = new Profile();
        profile.setFirstName(signUpRequest.getFirstName());
        profile.setLastName(signUpRequest.getLastName());
        profile.setIsVerified(false);
        return profile;
    }

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
       String token =
               authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public AuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyUsedException(String.format("Email %s is already in use.", signUpRequest.getEmail()));
        }

        if (userService.hasUserWithPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new PhoneNumberAlreadyUsedException(String.format("Phone number %s is already in use.", signUpRequest.getPhoneNumber()));
        }

        if (userService.hasUserWithUuid(signUpRequest.getUuid())) {
            throw new UuidAlreadyUsedException(String.format("UUID %s is already in use.", signUpRequest.getUuid()));
        }

        userService.saveUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getEmail(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    // Helper method to determine if the input is a phone number
    private boolean isPhoneNumber(String identifier) {
        return identifier.startsWith("+");
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setUid(signUpRequest.getUuid());
        user.setProvider(OAuth2Provider.LOCAL);
        user.setProfile(getProfile(signUpRequest));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        return user;
    }
}