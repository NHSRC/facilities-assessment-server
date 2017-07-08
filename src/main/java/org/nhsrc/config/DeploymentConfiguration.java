package org.nhsrc.config;

import org.nhsrc.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deployment_configuration")
public class DeploymentConfiguration extends AbstractEntity {
    @Column(name = "recording_mode", nullable = true)
    private boolean recordingMode;

    public boolean isRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(boolean recordingMode) {
        this.recordingMode = recordingMode;
    }
}