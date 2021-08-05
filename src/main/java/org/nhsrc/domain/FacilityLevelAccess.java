package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "facility_level_access")
public class FacilityLevelAccess extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
