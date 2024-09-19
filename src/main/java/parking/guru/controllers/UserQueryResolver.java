package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.User;
import parking.guru.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {
    private final UserService userService;

    // Query: Get all users
    @QueryMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Query: Get a user by ID
    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}
