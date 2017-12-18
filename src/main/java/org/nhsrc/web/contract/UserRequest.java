package org.nhsrc.web.contract;

public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userType;
    private int userTypeReferenceId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserTypeReferenceId() {
        return userTypeReferenceId;
    }

    public void setUserTypeReferenceId(int userTypeReferenceId) {
        this.userTypeReferenceId = userTypeReferenceId;
    }
}