package org.nhsrc.common;

import org.junit.runner.RunWith;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public abstract class AbstractIntegrationTest {
    protected void provideAuthority(String ... privileges) {
        List<GrantedAuthority> authorities = Privilege.createAuthorities(privileges);
        Authentication auth = new UsernamePasswordAuthenticationToken(User.INTEGRATION_TEST_USER_EMAIL, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}