package org.nhsrc.service;

import org.nhsrc.domain.security.Role;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.security.RoleRepository;
import org.nhsrc.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;

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
        String email = principal == null ? User.ANONYMOUS_USERS_EMAIL : principal.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = userRepository.findByEmail(User.ANONYMOUS_USERS_EMAIL);
        }
        return user;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}