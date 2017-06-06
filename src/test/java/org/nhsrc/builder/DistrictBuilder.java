package org.nhsrc.builder;

import org.nhsrc.domain.District;

import java.util.UUID;

public class DistrictBuilder {

    private final District district;

    public DistrictBuilder() {
        district = new District();
        district.setUuid(UUID.randomUUID());
    }

    public DistrictBuilder withName(String name) {
        district.setName(name);
        return this;
    }

    public District build() {
        return district;
    }

}
