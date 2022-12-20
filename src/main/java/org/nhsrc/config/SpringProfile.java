package org.nhsrc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class SpringProfile {
    public static final String DEV = "dev";
    public static final String TEST = "test";
    private final Environment environment;

    @Autowired
    public SpringProfile(Environment environment) {
        this.environment = environment;
    }

    public boolean isDev() {
        return isProfile(SpringProfile.DEV) || isProfile(SpringProfile.TEST);
    }

    private boolean isProfile(String profileName) {
        return Arrays.asList(environment.getActiveProfiles()).contains(profileName);
    }

    public String getProfiles() {
        return Arrays.stream(environment.getActiveProfiles()).map(s -> s).collect(Collectors.joining(","));
    }
}
