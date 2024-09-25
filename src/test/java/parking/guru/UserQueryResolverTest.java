package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parking.guru.exceptions.CustomGraphQLException;
import parking.guru.models.User;
import parking.guru.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserQueryResolverTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserQueryResolver userQueryResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserById() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        user.setEmail("user@example.com");

        when(userService.getUserById((long) userId)).thenReturn(Optional.of(user));

        User result = userQueryResolver.userById((long) userId);

        assertEquals(userId, result.getId());
        verify(userService, times(1)).getUserById((long) userId);
    }

    @Test
    void testUserByIdNotFound() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        assertThrows(CustomGraphQLException.class, () -> userQueryResolver.userById(userId));
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testUserByEmail() {
        String email = "user@example.com";

        User user = new User();
        user.setEmail(email);

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(user));

        User result = userQueryResolver.userByEmail(email);

        assertEquals(email, result.getEmail());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testUserByEmailNotFound() {
        String email = "nonexistent@example.com";

        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());

        assertThrows(CustomGraphQLException.class, () -> userQueryResolver.userByEmail(email));
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");
        users.add(user1);
        users.add(user2);

        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userQueryResolver.allUsers();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }
}
