package org.nhsrc.web;

import org.nhsrc.service.FacilityDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class FacilityDownloadController {
    private FacilityDownloadService facilityDownloadService;

    @Autowired
    public FacilityDownloadController(FacilityDownloadService facilityDownloadService) {
        this.facilityDownloadService = facilityDownloadService;
    }

    @RequestMapping(value = "nin/checkMetadata", method = {RequestMethod.GET})
    public void checkMetadata() {
        facilityDownloadService.checkMetadata();
    }
}