package edu.school21.service;


import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    private static final String LOGIN = "antoinco";

    private static final String INCORRECT_LOGIN = "anton";

    private static final String PASSWORD = "123";

    private static final String INCORRECT_PASSWORD = "321";

    @Mock
    UserRepository userRepository;

    @Test
    void testCorrectLoginPassword() {
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User user = new User(LOGIN,PASSWORD);
        Mockito.doReturn(user).when(userRepository).findByLogin(LOGIN);
        assertTrue(userService.authenticate(LOGIN,PASSWORD));
        Mockito.verify(userRepository).update(user);
    }

    @Test
    void testIncorrectLogin() {
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        Mockito.doReturn(null).when(userRepository).findByLogin(INCORRECT_LOGIN);
        assertFalse(userService.authenticate(INCORRECT_LOGIN,PASSWORD));
    }

    @Test
    void testIncorrectPassword() {
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User user = new User(LOGIN,PASSWORD);
        Mockito.doReturn(user).when(userRepository).findByLogin(LOGIN);
        assertFalse(userService.authenticate(LOGIN,INCORRECT_PASSWORD));
    }

    @Test
    void testThrows() {
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        User user = new User(LOGIN,PASSWORD);
        user.setStatus(true);
        Mockito.doReturn(user).when(userRepository).findByLogin(LOGIN);
        assertThrows(AlreadyAuthenticatedException.class, () -> userService.authenticate(LOGIN,PASSWORD));
    }
}
