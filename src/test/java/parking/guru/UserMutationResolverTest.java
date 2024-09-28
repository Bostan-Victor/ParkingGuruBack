package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parking.guru.dtos.CreateUserInput;
import parking.guru.dtos.UpdateUserInput;
import parking.guru.models.User;
import parking.guru.models.enums.Role;
import parking.guru.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserMutationResolverTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserMutationResolver userMutationResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        CreateUserInput input = new CreateUserInput();
        input.setEmail("user@example.com");
        input.setFirstName("Renatik");
        input.setLastName("PythonKing");
        input.setPhoneNumber("123123");
        input.setUid("UID123");
        input.setRole("USER");

        User user = new User();
        user.setEmail("user@example.com");
        user.setFirstName("Renatik");
        user.setLastName("PythonKing");
        user.setPhoneNumber("123123");
        user.setUid("UID123");
        user.setRole(Role.USER);

        when(userService.saveUser(any(User.class))).thenReturn(user);

        User createdUser = userMutationResolver.createUser(input);

        assertEquals("user@example.com", createdUser.getEmail());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        UpdateUserInput input = new UpdateUserInput();
        input.setEmail("updated@example.com");
        input.setFirstName("John Updated");
        input.setRole("USER");

        User user = new User();
        user.setId(userId);
        user.setEmail("user@example.com");
        user.setFirstName("John");

        when(userService.getUserById((long) userId)).thenReturn(Optional.of(user));
        when(userService.saveUser(any(User.class))).thenReturn(user);

        User updatedUser = userMutationResolver.updateUser((long) userId, input);

        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals("John Updated", updatedUser.getFirstName());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        Boolean result = userMutationResolver.deleteUser(userId);

        assertEquals(true, result);
        verify(userService, times(1)).deleteUser(userId);
    }
}
