package org.nhsrc.service;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.nhsrc.domain.metadata.EntityType;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    private static final String FIELDS_FOR_FACILITY_METADATA = "phc_chc_type,state_name,district_name";
    private static final String FIELDS_FOR_FACILITY = "phc_chc_type,state_name,district_name,nin_to_hfi,hfi_name";

    @Autowired
    public FacilityDownloadService(FacilityRepository facilityRepository, DistrictRepository districtRepository, StateRepository stateRepository, FacilityTypeRepository facilityTypeRepository, MissingNinEntityInLocalRepository missingNinEntityInLocalRepository, NinSyncDetailsRepository ninSyncDetailsRepository) {
        this.facilityRepository = facilityRepository;
        this.districtRepository = districtRepository;
        this.stateRepository = stateRepository;
        this.facilityTypeRepository = facilityTypeRepository;
        this.missingNinEntityInLocalRepository = missingNinEntityInLocalRepository;
        this.ninSyncDetailsRepository = ninSyncDetailsRepository;
    }

    public void download() {
        RestTemplate restTemplate = new RestTemplate();
        NINResponsePageDTO response;
        do {
            response = processOnePageForFacility(restTemplate);
        } while (!response.getResult().isSyncComplete());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected NINResponsePageDTO processOnePageForFacility(RestTemplate restTemplate) {
        try {
            NinSyncDetails ninSyncDetails = ninSyncDetailsRepository.findByType(NinSyncType.Facility);
            NINResponsePageDTO response = getNinResponsePageDTO(restTemplate, ninSyncDetails, FIELDS_FOR_FACILITY);

            List<RegisteredFacilityDTO> facilities = response.getData();
            for (RegisteredFacilityDTO registeredFacility : facilities) {
                if (registeredFacility.getFacilityType().equals(SUB_CENTRE))
                    continue;

                String stateName = registeredFacility.getState().replace("&", "and");
                State state = stateRepository.findByName(stateName);
                FacilityType facilityType = facilityTypeRepository.findByName(registeredFacility.getFacilityType());
                District district = districtRepository.findByNameAndState(registeredFacility.getDistrict(), state);
                if (district == null) {
                    district = new District(registeredFacility.getDistrict(), state);
                    district.setInactive(false);
                    districtRepository.save(district);
                }

                Facility facility = facilityRepository.findByNameAndDistrictAndFacilityType(registeredFacility.getFacilityName(), district, facilityType);
                if (facility == null) {
                    facility = new Facility();
                    facility.setName(registeredFacility.getFacilityName());
                    facility.setFacilityType(facilityType);
                    facility.setDistrict(district);
                    facility.setRegistryUniqueId(registeredFacility.getNinId());
                    facility.setInactive(false);
                } else {
                    facility.setRegistryUniqueId(registeredFacility.getNinId());
                }
                facilityRepository.save(facility);

                ResponseResultDTO result = response.getResult();
                ninSyncDetails.setOffsetSuccessfullyProcessed(result.getNextOffset());
                ninSyncDetailsRepository.save(ninSyncDetails);
            }
            return response;
        } catch (Exception e) {
            logger.error("Sync failed/stopped", e);
            throw e;
        }
    }

    public void checkMetadata() {
        RestTemplate restTemplate = new RestTemplate();
        NINResponsePageDTO response;
        do {
            response = processOnePageForMetadata(restTemplate);
        } while (!response.getResult().isSyncComplete());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected NINResponsePageDTO processOnePageForMetadata(RestTemplate restTemplate) {
        try {
            NinSyncDetails ninSyncDetails = ninSyncDetailsRepository.findByType(NinSyncType.FacilityMetadata);
            NINResponsePageDTO response = getNinResponsePageDTO(restTemplate, ninSyncDetails, FIELDS_FOR_FACILITY_METADATA);

            List<RegisteredFacilityDTO> facilities = response.getData();
            for (RegisteredFacilityDTO registeredFacility : facilities) {
                if (registeredFacility.getFacilityType().equals(SUB_CENTRE)) continue;

                String stateName = registeredFacility.getState().replace("&", "and");
                State state = stateRepository.findByName(stateName);
                if (state == null && missingNinEntityInLocalRepository.findByNameAndType(stateName, EntityType.State) == null) {
                    logger.info(String.format("New missing state %s", stateName));
                    missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(stateName, EntityType.State));
                } else if (state == null) {
                    continue;
                }

                FacilityType facilityType = facilityTypeRepository.findByName(registeredFacility.getFacilityType());
                if (facilityType == null && missingNinEntityInLocalRepository.findByNameAndType(registeredFacility.getFacilityType(), EntityType.FacilityType) == null) {
                    logger.info(String.format("New missing facility type %s", registeredFacility.getFacilityType()));
                    missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(registeredFacility.getFacilityType(), EntityType.FacilityType));
                }
            }
            ResponseResultDTO result = response.getResult();
            ninSyncDetails.setOffsetSuccessfullyProcessed(result.getNextOffset());
            ninSyncDetailsRepository.save(ninSyncDetails);
            return response;
        } catch (Exception e) {
            logger.error("Sync failed/stopped", e);
            throw e;
        }
    }

    private NINResponsePageDTO getNinResponsePageDTO(RestTemplate restTemplate, NinSyncDetails ninSyncDetails, String fields) {
        NINResponsePageDTO response;
        StringBuilder stringBuilder = new StringBuilder("https://nin.nhp.gov.in/api/facilities?api-key=SdafdfeDSF45r4dfdf5FFGcDAfa4eb88CN70da985&fields=");
        stringBuilder.append(fields);
        stringBuilder.append("&offset=");
        logger.info(String.format("Making API call with offset %d", ninSyncDetails.getOffsetSuccessfullyProcessed()));
        stringBuilder.append(ninSyncDetails.getOffsetSuccessfullyProcessed()).append("&limit=100");
        response = restTemplate.getForObject(stringBuilder.toString(), NINResponsePageDTO.class);
        return response;
    }
}