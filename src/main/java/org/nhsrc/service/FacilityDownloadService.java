package org.nhsrc.service;

import org.nhsrc.domain.District;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.nhsrc.domain.nin.*;
import org.nhsrc.dto.nin.NINResponsePageDTO;
import org.nhsrc.dto.nin.RegisteredFacilityDTO;
import org.nhsrc.dto.nin.ResponseResultDTO;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.nin.MissingNinEntityInLocalRepository;
import org.nhsrc.repository.nin.NinSyncDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class FacilityDownloadService {
    private FacilityRepository facilityRepository;
    private DistrictRepository districtRepository;
    private StateRepository stateRepository;
    private FacilityTypeRepository facilityTypeRepository;
    private MissingNinEntityInLocalRepository missingNinEntityInLocalRepository;
    private NinSyncDetailsRepository ninSyncDetailsRepository;
    private static Logger logger = LoggerFactory.getLogger(FacilityDownloadService.class);

    private static final String SUB_CENTRE = "SubCentre";
    private static final int PAGE_SIZE = 50;

    @Autowired
    public FacilityDownloadService(FacilityRepository facilityRepository, DistrictRepository districtRepository, StateRepository stateRepository, FacilityTypeRepository facilityTypeRepository, MissingNinEntityInLocalRepository missingNinEntityInLocalRepository, NinSyncDetailsRepository ninSyncDetailsRepository) {
        this.facilityRepository = facilityRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.missingNinEntityInLocalRepository = missingNinEntityInLocalRepository;
        this.ninSyncDetailsRepository = ninSyncDetailsRepository;
    }

    public void download() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        NINResponsePageDTO response = restTemplate.getForObject("https://nin.nhp.gov.in/api/facilities?api-key=SdafdfeDSF45r4dfdf5FFGcDAfa4eb88CN70da985&offset=0&limit=2", NINResponsePageDTO.class);
        System.out.println(response);
    }

    public void checkMetadata() {
        RestTemplate restTemplate = new RestTemplate();
        NINResponsePageDTO response = null;

        do {
            try {
                NinSyncDetails ninSyncDetails = ninSyncDetailsRepository.findByType(NinSyncType.FacilityMetadata);
                StringBuilder stringBuilder = new StringBuilder("https://nin.nhp.gov.in/api/facilities?api-key=SdafdfeDSF45r4dfdf5FFGcDAfa4eb88CN70da985&fields=phc_chc_type,state_name,district_name&offset=");
                logger.info(String.format("Making API call with offset %d", ninSyncDetails.getOffsetSuccessfullyProcessed()));
                stringBuilder.append(ninSyncDetails.getOffsetSuccessfullyProcessed()).append("&limit=100");
                response = restTemplate.getForObject(stringBuilder.toString(), NINResponsePageDTO.class);

                List<RegisteredFacilityDTO> facilities = response.getData();
                for (RegisteredFacilityDTO registeredFacility : facilities) {
                    String stateName = registeredFacility.getState().replace("&", "and");
                    State state = stateRepository.findByName(stateName);
                    if (state == null && missingNinEntityInLocalRepository.findByNameAndType(stateName, FacilityEntityType.State) == null) {
                        logger.info(String.format("New missing state %s", stateName));
                        missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(stateName, FacilityEntityType.State));
                    } else if (state == null) {
                        continue;
                    }

                    FacilityType facilityType = facilityTypeRepository.findByName(registeredFacility.getFacilityType());
                    if (!registeredFacility.getFacilityType().equals(SUB_CENTRE) && facilityType == null && missingNinEntityInLocalRepository.findByNameAndType(registeredFacility.getFacilityType(), FacilityEntityType.FacilityType) == null) {
                        logger.info(String.format("New missing facility type %s", registeredFacility.getFacilityType()));
                        missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(registeredFacility.getFacilityType(), FacilityEntityType.FacilityType));
                    } else if (!registeredFacility.getFacilityType().equals(SUB_CENTRE) && facilityType == null) {
                        continue;
                    }

                    List<District> district = districtRepository.findByNameAndState(registeredFacility.getDistrict(), state);
                    if (district == null && missingNinEntityInLocalRepository.findByNameAndType(registeredFacility.getDistrict(), FacilityEntityType.District) == null) {
                        logger.info(String.format("New missing district %s", registeredFacility.getDistrict()));
                        missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(registeredFacility.getDistrict(), FacilityEntityType.District));
                    }
                }
                ResponseResultDTO result = response.getResult();
                ninSyncDetails.setOffsetSuccessfullyProcessed(result.getNextOffset());
                ninSyncDetailsRepository.save(ninSyncDetails);
            } catch (Exception e) {
                logger.error("Sync failed/stopped", e);
                break;
            }
        } while (!response.getResult().isSyncComplete());
    }
}