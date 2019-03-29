package org.nhsrc.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nhsrc.common.AbstractIntegrationTest;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.Role;
import org.nhsrc.repository.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class RoleRepositoryIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void privilegesMapping() {
        this.provideAuthority(Privilege.USERS_WRITE.getSpringName());
        Role adminRole = roleRepository.findByName(Role.ADMIN);
        assertNotEquals(0, adminRole.getPrivileges().size());
    }
}