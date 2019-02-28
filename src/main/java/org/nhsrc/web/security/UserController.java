package org.nhsrc.web.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "users", method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize(value = "hasAnyAuthority('User_Write')")
    @Transactional
    public User save(@RequestBody UserRequest userRequest) {
        User user = Repository.findByUuidOrId(userRequest.getUuid(), userRequest.getId(), userRepository);
        if (user == null) {
            user = new User();
            user.setUuid(UUID.randomUUID());
        }
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setInactive(userRequest.getInactive());
        return userService.saveUser(user);
    }

    @RequestMapping(value = "users/first", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public User createFirstUser(@RequestBody UserRequest userRequest) {
        userRequest.setInactive(true);
        return this.save(userRequest);
    }

    @RequestMapping(value = "currentUser", method = RequestMethod.GET)
    public User loggedInUser(Principal principal) {
        String name = principal.getName();
        return userService.findUserByEmail(name);
    }

    @RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
    public String loginSuccess() {
        return "Successful Login";
    }
}