package org.nhsrc.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.*;
import org.nhsrc.domain.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setAssessmentNumber(String assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public List<Integer> getUserIds() {
        return users.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public String getAssessmentNumber() {
        return assessmentNumber;
    }

    @JsonProperty("facilityId")
    public int _getFacilityId() {
        return facility.getId();
    }

    @JsonProperty("assessmentTypeId")
    public int _getAssessmentTypeId() {
        return assessmentType.getId();
    }

    @JsonProperty("userIds")
    public List<Integer> _getUserIds() {
        return users.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @JsonProperty("stateId")
    public int _getStateId() {
        return facility.getDistrict().getState().getId();
    }

    @JsonProperty("districtId")
    public int _getDistrictId() {
        return facility.getDistrict().getId();
    }

    @JsonProperty("facilityTypeId")
    public int _getFacilityTypeId() {
        return facility.getFacilityType().getId();
    }

    @JsonProperty("assessmentToolModeId")
    public int _getAssessmentToolModeId() {
        return assessmentType.getAssessmentToolMode().getId();
    }
}
