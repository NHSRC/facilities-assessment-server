package org.nhsrc.config;

import org.nhsrc.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deployment_app_configuration")
public class DeploymentAppConfiguration extends AbstractEntity {
    @Column(name = "settings_enabled", nullable = true)
    private boolean settingsEnabled;

    @Column(name = "seed_data_packaged", nullable = true)
    private boolean seedDataPackaged;

    public boolean isSettingsEnabled() {
        return settingsEnabled;
    }

    public void setSettingsEnabled(boolean settingsEnabled) {
        this.settingsEnabled = settingsEnabled;
    }

    public boolean isSeedDataPackaged() {
        return seedDataPackaged;
    }

    public void setSeedDataPackaged(boolean seedDataPackaged) {
        this.seedDataPackaged = seedDataPackaged;
    }
}