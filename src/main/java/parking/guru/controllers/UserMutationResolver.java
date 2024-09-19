package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.CreateUserInput;
import parking.guru.models.Profile;
import parking.guru.models.UpdateUserInput;
import parking.guru.models.User;
import parking.guru.models.enums.Role;
import parking.guru.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserMutationResolver {

    private final UserService userService;

    // Mutation: Create a user
    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        // Manually map CreateUserInput to a User object
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setRole(Role.valueOf(input.getRole().toUpperCase()));

        // Create and set Profile
        Profile profile = new Profile();
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        user.setProfile(profile);

        // Pass the User object to saveUser() method
        return userService.saveUser(user);
    }

    // Mutation: Update a user
    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UpdateUserInput input) {
        return userService.updateUser(id, input);
    }

    // Mutation: Delete a user
    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
}
