package org.nhsrc.domain.nin;

import org.nhsrc.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "nin_sync_details")
public class NinSyncDetails extends BaseEntity {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NinSyncType type;
    @Column(name = "offset_successfully_processed")
    private int offsetSuccessfullyProcessed;

    public NinSyncDetails() {
    }

    public NinSyncDetails(NinSyncType type, int offsetSuccessfullyProcessed, int errorOnOffset) {
        this.type = type;
        this.offsetSuccessfullyProcessed = offsetSuccessfullyProcessed;
    }

    public NinSyncType getType() {
        return type;
    }

    public void setType(NinSyncType type) {
        this.type = type;
    }

    public int getOffsetSuccessfullyProcessed() {
        return offsetSuccessfullyProcessed;
    }

    public void setOffsetSuccessfullyProcessed(int offsetSuccessfullyProcessed) {
        this.offsetSuccessfullyProcessed = offsetSuccessfullyProcessed;
    }
}