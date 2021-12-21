package org.nhsrc.dto.nin;

import java.util.ArrayList;
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

    public static NINResponsePageDTO noContent() {
        NINResponsePageDTO ninResponsePageDTO = new NINResponsePageDTO();
        ResponseResultDTO responseResultDTO = new ResponseResultDTO();
        responseResultDTO.setCount(0);
        responseResultDTO.setOffset(0);
        responseResultDTO.setTotalRecords("0");
        ninResponsePageDTO.setResult(responseResultDTO);
        ninResponsePageDTO.setData(new ArrayList<>());
        return ninResponsePageDTO;
    }
}
