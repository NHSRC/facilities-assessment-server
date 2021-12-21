package org.nhsrc.domain.nin;

import org.nhsrc.domain.BaseEntity;
import org.nhsrc.utils.DateUtils;

import javax.persistence.*;

@Entity
@Table(name = "nin_sync_details")
public class NinSyncDetails extends BaseEntity {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NinSyncType type;

    @Column(name = "offset_successfully_processed")
    private int offsetSuccessfullyProcessed;

    @Column(name = "has_more_for_date")
    private boolean hasMoreForDate;

    @Column(name = "date_processed_upto")
    private String dateProcessedUpto;

    public NinSyncDetails() {
    }

    public NinSyncType getType() {
        return type;
    }

    public void setType(NinSyncType type) {
        this.type = type;
    }

    public String getDateProcessedUpto() {
        return dateProcessedUpto;
    }

    public void setDateProcessedUpto(String dateProcessedUpto) {
        this.dateProcessedUpto = dateProcessedUpto;
    }

    public int getOffsetSuccessfullyProcessed() {
        return offsetSuccessfullyProcessed;
    }

    public void setOffsetSuccessfullyProcessed(int offsetSuccessfullyProcessed) {
        this.offsetSuccessfullyProcessed = offsetSuccessfullyProcessed;
    }

    public boolean isHasMoreForDate() {
        return hasMoreForDate;
    }

    public void setHasMoreForDate(boolean hasMoreForDate) {
        this.hasMoreForDate = hasMoreForDate;
    }

    public PageToRequest nextPage() {
        if (hasMoreForDate)
            return new PageToRequest(offsetSuccessfullyProcessed, dateProcessedUpto);
        else
            return new PageToRequest(0, DateUtils.nextDate(dateProcessedUpto));
    }

    public class PageToRequest {
        private int offset;
        private String date;

        public PageToRequest(int offset, String date) {
            this.offset = offset;
            this.date = date;
        }

        public int getOffset() {
            return offset;
        }

        public String getDate() {
            return date;
        }
    }
}
