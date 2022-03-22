package org.nhsrc.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.nhsrc.domain.*;
import org.nhsrc.domain.security.User;
import org.springframework.data.rest.core.annotation.RestResource;

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
    @RestResource(exported = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_type_id")
    @NotNull
    @RestResource(exported = false)
    private AssessmentType assessmentType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "assessment_number_assignment_users", joinColumns = @JoinColumn(name = "assessment_number_assignment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonIgnore
    @RestResource(exported = false)
    private Set<User> users = new HashSet<>();

    @Column(name = "assessment_number")
    private String assessmentNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment_tool_id")
    @NotNull
    @RestResource(exported = false)
    private AssessmentTool assessmentTool;

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setAssessmentType(AssessmentType assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setAssessmentNumber(String assessmentNumber) {
        this.assessmentNumber = assessmentNumber;
    }

    public void setAssessmentTool(AssessmentTool assessmentTool) {
        this.assessmentTool = assessmentTool;
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

    @JsonProperty("assessmentToolId")
    public int _getAssessmentToolId() {
        return assessmentTool.getId();
    }
}
