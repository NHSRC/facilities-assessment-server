package org.nhsrc.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.nhsrc.domain.AbstractEntity;
import org.nhsrc.domain.AssessmentType;
import org.nhsrc.domain.Checklist;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "assessment_number_assignment")
public class AssessmentNumberAssignment extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_type_id")
    @NotNull
    private AssessmentType assessmentType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "assessment_number_assignment_users", joinColumns = @JoinColumn(name = "assessment_number_assignment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @Column(name = "assessment_number")
    private String assessmentNumber;
}
