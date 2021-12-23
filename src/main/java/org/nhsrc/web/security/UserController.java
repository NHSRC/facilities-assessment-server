package org.nhsrc.web.security;

import org.nhsrc.domain.security.Role;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.security.RoleRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.LoginResponse;
import org.nhsrc.web.contract.UserProfileRequest;
import org.nhsrc.web.contract.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EntityManager entityManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entityManager = entityManager;
    }

    @RequestMapping(value = "users", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public User save(@RequestBody UserRequest userRequest) {
        User newEntity = new User();
        newEntity.setUuid(UUID.randomUUID());
        final User user = Repository.findByIdOrCreate(userRequest.getId(), userRepository, newEntity);
        if (user.getPassword() == null) {
            user.setPasswordChanged(false);
        }
        if (user.getPassword() != null && userRequest.hasPassword()) {
            user.setPasswordChanged(true);
        }
        if (userRequest.getPassword() != null && userRequest.hasPassword()) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setInactive(userRequest.getInactive());
        Repository.mergeChildren(userRequest.getRoleIds(), user.getRoleIds(), roleRepository, role -> user.removeRole((Role) role), role -> user.addRole((Role) role));
        return userService.saveUser(user);
    }

    @RequestMapping(value = "currentUser", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('User')")
    public ResponseEntity saveProfile(@RequestBody UserProfileRequest userProfileRequest, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        if (changingPassword(userProfileRequest) && !bCryptPasswordEncoder.matches(userProfileRequest.getOldPassword(), user.getPassword()))
            return new ResponseEntity<>("Old password doesn't match", HttpStatus.BAD_REQUEST);
        if (changingPassword(userProfileRequest) && userProfileRequest.getNewPassword() != null && !userProfileRequest.getNewPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userProfileRequest.getNewPassword()));
        } else if (changingPassword(userProfileRequest) && (userProfileRequest.getNewPassword() == null || userProfileRequest.getNewPassword().isEmpty() || userProfileRequest.getNewPassword().length() < 8)) {
            return new ResponseEntity<>("New password cannot be empty or less than 8 characters", HttpStatus.BAD_REQUEST);
        }
        user.setEmail(userProfileRequest.getEmail());
        user.setFirstName(userProfileRequest.getFirstName());
        user.setLastName(userProfileRequest.getLastName());
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    private boolean changingPassword(@RequestBody UserProfileRequest userProfileRequest) {
        return userProfileRequest.getOldPassword() != null &&
                !userProfileRequest.getOldPassword().isEmpty();
    }

    @RequestMapping(value = "users/first", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public User createFirstUser(@RequestBody UserRequest userRequest) {
        userRequest.setInactive(true);
        return this.save(userRequest);
    }

    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    @PreAuthorize("hasRole('User')")
    public User loggedInUser(Principal principal) {
        String name = principal.getName();
        return userService.findUserByEmail(name);
    }

    @RequestMapping(value = "/users/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public User delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, userRepository);
    }

    @PreAuthorize("hasRole('Users_Write')")
    @RequestMapping(value = "/user/search/find", method = {RequestMethod.GET})
    public List<User> find(@RequestParam(value = "q") String q) {
        return entityManager.createQuery("SELECT u FROM User u ORDER BY u.email", User.class).setMaxResults(100).getResultList();
    }
}
