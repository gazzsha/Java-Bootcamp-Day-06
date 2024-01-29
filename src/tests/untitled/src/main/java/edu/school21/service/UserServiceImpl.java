package edu.school21.service;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UserRepository;

public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean authenticate(String login,String password) {
        User user = userRepository.findByLogin(login);
        if (user == null) return false;
        if (user.getStatus()) throw new AlreadyAuthenticatedException();
        if (password.equals(user.getPassword())) {
            user.setStatus(true);
            userRepository.update(user);
            return true;
        }
        return false;
    }
}
