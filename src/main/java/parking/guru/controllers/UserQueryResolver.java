package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.User;
import parking.guru.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    private final UserService userService;

    @QueryMapping
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User userById(@Argument Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    @QueryMapping
    public User userByEmail(@Argument String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
