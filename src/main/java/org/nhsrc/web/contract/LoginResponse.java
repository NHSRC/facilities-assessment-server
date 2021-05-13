package org.nhsrc.web.contract;

public class LoginResponse {
    private boolean passwordChanged;

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
