package org.nhsrc.dto.nin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NINResponsePageDTO {
    private List<RegisteredFacilityDTO> data;
    private ResponseResultDTO result;

    public List<RegisteredFacilityDTO> getData() {
        return data;
    }

    public void setData(List<RegisteredFacilityDTO> data) {
        this.data = data;
    }

    public ResponseResultDTO getResult() {
        return result;
    }

    public void setResult(ResponseResultDTO result) {
        this.result = result;
    }
}