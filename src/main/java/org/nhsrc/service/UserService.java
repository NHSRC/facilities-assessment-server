package org.nhsrc.service;

import org.nhsrc.domain.security.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveUser(User user);
    int findIdForUserType(String userTypeName, String userTypeReferenceName);
}
