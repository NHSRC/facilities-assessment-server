package org.nhsrc.service;

import org.nhsrc.domain.security.PrivilegeName;
import org.nhsrc.domain.security.User;

import java.security.Principal;

public interface UserService {
    User findUserByEmail(String email);
    User findUserByPrincipal(Principal principal);
    User findSubmissionUser(Principal principal);
    User saveUser(User user);
    boolean hasAllStatesDashboardPrivilege();
}
