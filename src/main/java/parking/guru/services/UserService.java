package parking.guru.services;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserByUserEmail(String username) {
        return userRepository.findByUserEmail(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User validateAndGetUserByUsername(String username) {
        return getUserByUserEmail(username)
                .orElseThrow(() -> new RuntimeException(String.format("User with username %s not found", username)));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
