package org.nhsrc.web.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.security.UserRepository;
import org.nhsrc.service.UserService;
import org.nhsrc.web.contract.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.security.Principal;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @Transactional
    public void createNewUser(@RequestBody UserRequest userRequest) {
        User user = userService.findUserByEmail(userRequest.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(userRequest.getEmail());
        }
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setInactive(true);

        user.setUserType(userRequest.getUserType());
        int userTypeId = userService.findIdForUserType(userRequest.getUserType(), userRequest.getUserTypeName());
        user.setUserTypeReferenceId(userTypeId);
        userService.saveUser(user);
    }

    @RequestMapping(value = "/api/currentUser", method = RequestMethod.GET)
    public User loggedInUser(Principal principal) {
        String name = principal.getName();
        return userService.findUserByEmail(name);
    }

    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess() {
        return "Successful Login";
    }
}