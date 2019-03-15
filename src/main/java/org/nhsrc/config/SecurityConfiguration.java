package org.nhsrc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@Configuration
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static Authentication anonymousAuthentication;

    static {
        ArrayList<SimpleGrantedAuthority> anonymousAuthorities = new ArrayList<>();
        anonymousAuthorities.add(new SimpleGrantedAuthority("ROLE_User"));
        anonymousAuthentication = new AnonymousAuthenticationToken("foo", "foo", anonymousAuthorities);
    }

    public static Authentication getAnonymousAuthentication() {
        return anonymousAuthentication;
    }
}