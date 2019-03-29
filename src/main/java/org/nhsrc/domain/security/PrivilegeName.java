package org.nhsrc.domain.security;

public class PrivilegeName {
    private String name;

    public PrivilegeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSpringName() {
        return String.format("ROLE_%s", name);
    }
}