package org.nhsrc.dto.nin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseResultDTO {
    @JsonProperty("count")
    private int count;

    @JsonProperty("total_records")
    private String totalRecords;

    @JsonProperty("offset")
    private int offset;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public int getTotalNumberOfRecords() {
        return Integer.parseInt(this.getTotalRecords());
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isSyncComplete() {
        return !(this.getOffset() + this.getCount() < this.getTotalNumberOfRecords());
    }

    public int getNextOffset() {
        return this.getOffset() + this.getCount();
    }
}