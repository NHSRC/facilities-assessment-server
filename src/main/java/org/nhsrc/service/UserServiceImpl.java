package org.nhsrc.service;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findSubmissionUser(Principal principal) {
        if (principal == null) {
            return userRepository.findByEmail(User.ANONYMOUS_USERS_EMAIL);
        } else {
            return userRepository.findByEmail(principal.getName());
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}