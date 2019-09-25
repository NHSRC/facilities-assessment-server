package org.nhsrc.web.security;

import org.nhsrc.domain.security.Role;
import org.nhsrc.domain.security.User;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.security.RoleRepository;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "login", method = {RequestMethod.GET})
    public ResponseEntity login(Principal principal) {
        if (principal == null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "users", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public User save(@RequestBody UserRequest userRequest) {
        User newEntity = new User();
        newEntity.setUuid(UUID.randomUUID());
        final User user = Repository.findByIdOrCreate(userRequest.getId(), userRepository, newEntity);
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setInactive(userRequest.getInactive());
        Repository.mergeChildren(userRequest.getRoleIds(), user.getRoleIds(), roleRepository, role -> user.removeRole((Role)role), role -> user.addRole((Role) role));
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

    @RequestMapping(value = "/users/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Users_Write')")
    public User delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, userRepository);
    }
}