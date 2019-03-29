package org.nhsrc.domain.security;

import org.junit.Test;
import org.nhsrc.domain.AssessmentToolMode;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void hasPrivilege() {
        User user = new User();
        Role role = new Role();
        Privilege privilege = new Privilege();
        privilege.setName(Privilege.ASSESSMENT_READ.getName());
        AssessmentToolMode assessmentToolMode = new AssessmentToolMode();
        assessmentToolMode.setName("LAQSHYA");
        privilege.setAssessmentToolMode(assessmentToolMode);
        role.addPrivilege(privilege);
        user.addRole(role);

        assertTrue(user.hasPrivilege(Privilege.ASSESSMENT_READ, assessmentToolMode.getName()));
        assertFalse(user.hasPrivilege(Privilege.ASSESSMENT_READ, "foo"));
    }
}