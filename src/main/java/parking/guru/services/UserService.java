package parking.guru.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import parking.guru.models.User;
import parking.guru.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) { return userRepository.save(user); }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByEmailOrPhoneNumber(String username) {
        return userRepository.findByEmailOrPhoneNumber(username);
    }

    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean hasUserWithPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean hasUserWithUuid(String uuid) {
        return userRepository.existsByUid(uuid);
    }

    public User validateAndGetUserByEmail(String email) {
        return getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException(String.format("User with email %s not found", email)));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User with phone number not found"));
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }

    public boolean isUserVerified(String email) {
        User user = validateAndGetUserByEmail(email);
        return user.getIsVerified();
    }
}

