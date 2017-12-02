package org.nhsrc.service;

import org.nhsrc.domain.security.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
