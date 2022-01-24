package org.nhsrc.service;

import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.security.PrivilegeRepository;
import org.nhsrc.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByPrincipal(Principal principal) {
        String email = principal.getName();
        if (email == null || email.isEmpty()) return null;
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

    @Override
    public boolean hasAllStatesDashboardPrivilege() {
        return privilegeRepository.findByName(Privilege.ALL_STATES_DASHBOARD.getName()) != null;
    }

    @Override
    public boolean hasStatePrivilege(String stateName, User user) {
        if (hasAllStatesDashboardPrivilege()) return true;
        return user.hasStatePrivilege(stateName);
    }
}
