package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.User;
import parking.guru.models.enums.Role;
import parking.guru.services.UserService;
import parking.guru.dtos.CreateUserInput;
import parking.guru.dtos.UpdateUserInput;

@Controller
@RequiredArgsConstructor
public class UserMutationResolver {

    private final UserService userService;

    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setPhoneNumber(input.getPhoneNumber());
        user.setUid(input.getUid());
        user.setRole(Role.valueOf(input.getRole()));

        return userService.saveUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UpdateUserInput input) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (input.getEmail() != null) {
            user.setEmail(input.getEmail());
        }
        if (input.getFirstName() != null) {
            user.setFirstName(input.getFirstName());
        }
        if (input.getLastName() != null) {
            user.setLastName(input.getLastName());
        }
        if (input.getPhoneNumber() != null) {
            user.setPhoneNumber(input.getPhoneNumber());
        }
        if (input.getRole() != null) {
            user.setRole(Role.valueOf(input.getRole()));
        }

        return userService.saveUser(user);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        userService.deleteUser(id);
        return true;
    }
}
