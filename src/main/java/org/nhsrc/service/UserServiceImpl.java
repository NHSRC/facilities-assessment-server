package org.nhsrc.service;

import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.security.Role;
import org.nhsrc.domain.security.User;
import org.nhsrc.domain.security.UserType;
import org.nhsrc.repository.AssessmentToolModeRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.security.RoleRepository;
import org.nhsrc.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AssessmentToolModeRepository assessmentToolModeRepository;

    @Autowired
    private StateRepository stateRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findSubmissionUser(String email) {
        if (email == null) {
            return userRepository.findByUserType(UserType.AnonymousAssessor.toString());
        } else {
            User loggedInUser = this.findUserByEmail(email);
            if (loggedInUser == null)
                return userRepository.findByUserType(UserType.AnonymousAssessor.toString());
            return loggedInUser;
        }
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public int findIdForUserType(String userTypeName, String userTypeReferenceName) {
        UserType userType = UserType.valueOf(userTypeName);
        AbstractEntity entity;
        if (UserType.State.equals(userType))
            entity = stateRepository.findByName(userTypeReferenceName);
        else if (UserType.AssessmentToolMode.equals(userType))
            entity = assessmentToolModeRepository.findByName(userTypeReferenceName);
        else
            throw new RuntimeException("User of type anonymous cannot be created");
        return entity.getId();
    }
}