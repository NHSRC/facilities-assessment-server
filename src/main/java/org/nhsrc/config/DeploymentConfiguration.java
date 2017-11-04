package org.nhsrc.config;

import org.hibernate.annotations.GenericGenerator;
import org.nhsrc.domain.AbstractTransactionalEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "deployment_configuration")
public class DeploymentConfiguration extends AbstractTransactionalEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(name = "recording_mode", nullable = true)
    private boolean recordingMode;

    public boolean isRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(boolean recordingMode) {
        this.recordingMode = recordingMode;
    }
}