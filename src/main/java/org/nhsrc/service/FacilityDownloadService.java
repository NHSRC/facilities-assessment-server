package org.nhsrc.service;

import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.nhsrc.domain.FacilityType;
import org.nhsrc.domain.State;
import org.nhsrc.domain.metadata.EntityType;
import org.nhsrc.domain.nin.MissingNinEntityInLocal;
import org.nhsrc.domain.nin.NinSyncDetails;
import org.nhsrc.domain.nin.NinSyncType;
import org.nhsrc.dto.nin.NINResponsePageDTO;
import org.nhsrc.dto.nin.RegisteredFacilityDTO;
import org.nhsrc.dto.nin.ResponseResultDTO;
import org.nhsrc.repository.DistrictRepository;
import org.nhsrc.repository.FacilityRepository;
import org.nhsrc.repository.FacilityTypeRepository;
import org.nhsrc.repository.StateRepository;
import org.nhsrc.repository.nin.MissingNinEntityInLocalRepository;
import org.nhsrc.repository.nin.NinSyncDetailsRepository;
import org.nhsrc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FacilityDownloadService {
    private final FacilityRepository facilityRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final FacilityTypeRepository facilityTypeRepository;
    private final MissingNinEntityInLocalRepository missingNinEntityInLocalRepository;
    private final NinSyncDetailsRepository ninSyncDetailsRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacilityDownloadService.class);

    private static final int PAGE_SIZE = 100;
    private static final String FIELDS_FOR_FACILITY = "phc_chc_type,state_name,district_name,nin_to_hfi,hfi_name";

    @Value("${nin.apiKey}")
    private String apiKey;

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
        boolean moreDownloadsAvailable;
        do {
            moreDownloadsAvailable = processOnePageForFacility(restTemplate);
        } while (moreDownloadsAvailable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected boolean processOnePageForFacility(RestTemplate restTemplate) {
        NinSyncDetails ninSyncDetails = ninSyncDetailsRepository.findByType(NinSyncType.Facility);
        NinSyncDetails.PageToRequest page = ninSyncDetails.nextPage();
        if (LocalDate.now().minus(1, ChronoUnit.DAYS).equals(DateUtils.getDate(page.getDate()))) {
            logger.info(String.format("Processed upto %s, no more pages to process", ninSyncDetails.getDateProcessedUpto()));
            return false;
        }

        NINResponsePageDTO response = getNinResponsePageDTO(restTemplate, page);

        List<RegisteredFacilityDTO> facilities = response.getData();
        logger.info(String.format("Got %d facilities to process for date=%s, offset=%d", facilities.size(), page.getDate(), page.getOffset()));
        for (RegisteredFacilityDTO registeredFacility : facilities) {
            try {
                processMetadata(registeredFacility);

                State state = stateRepository.findByName(registeredFacility.getStateName());
                District district = districtRepository.findByNameAndState(registeredFacility.getDistrict(), state);
                if (district == null) {
                    district = new District(registeredFacility.getDistrict(), state);
                    district.setInactive(false);
                    districtRepository.save(district);
                } else if (district.getInactive()) {
                    district.setInactive(false);
                    districtRepository.save(district);
                }

                FacilityType facilityType = facilityTypeRepository.findByName(registeredFacility.getFacilityType());
                Facility ninMatch = facilityRepository.findByRegistryUniqueIdAndInactiveFalse(registeredFacility.getNinId());
                Facility semanticMatch = facilityRepository.findByNameAndDistrictAndFacilityType(registeredFacility.getFacilityName(), district, facilityType);
                if (ninMatch == null && semanticMatch == null) {
                    ninMatch = new Facility();
                    ninMatch.setName(registeredFacility.getFacilityName());
                    ninMatch.setFacilityType(facilityType);
                    ninMatch.setDistrict(district);
                    ninMatch.setRegistryUniqueId(registeredFacility.getNinId());
                    facilityRepository.save(ninMatch);
                } else if (ninMatch == null && semanticMatch != null) {
                    semanticMatch.setRegistryUniqueId(registeredFacility.getNinId());
                    facilityRepository.save(semanticMatch);
                } else if (ninMatch != null && semanticMatch == null) {
                    ninMatch.setName(registeredFacility.getFacilityName());
                    ninMatch.setFacilityType(facilityType);
                    ninMatch.setDistrict(district);
                    facilityRepository.save(ninMatch);
                } else if (ninMatch != null && semanticMatch != null && ninMatch.getRegistryUniqueId().equals(semanticMatch.getRegistryUniqueId())) {
//                    do nothing
                } else if (ninMatch != null && semanticMatch != null && !ninMatch.getRegistryUniqueId().equals(semanticMatch.getRegistryUniqueId())) {
                    ninMatch.setName(registeredFacility.getFacilityName());
                    ninMatch.setFacilityType(facilityType);
                    ninMatch.setDistrict(district);
                    facilityRepository.save(ninMatch);
                }
            } catch (Exception e) {
                logger.error(String.format("Sync failed/stopped while processing registered facility: %s", registeredFacility.getNinId()), e);
                throw e;
            }
        }
        updateSyncDetails(ninSyncDetails, page, response);
        return true;
    }

    private void updateSyncDetails(NinSyncDetails ninSyncDetails, NinSyncDetails.PageToRequest page, NINResponsePageDTO response) {
        ResponseResultDTO result = response.getResult();
        ninSyncDetails.setDateProcessedUpto(page.getDate());
        ninSyncDetails.setOffsetSuccessfullyProcessed(result.getNextOffset());
        ninSyncDetails.setHasMoreForDate((result.getTotalNumberOfRecords() - result.getNextOffset()) > 0);
        ninSyncDetailsRepository.save(ninSyncDetails);
        logger.info("Update sync details");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void processMetadata(RegisteredFacilityDTO registeredFacility) {
        String stateName = registeredFacility.getStateName();
        State state = stateRepository.findByName(stateName);
        if (state == null) {
            missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(stateName, EntityType.State));
            throw new RuntimeException(String.format("New missing state %s", stateName));
        }

        FacilityType facilityType = facilityTypeRepository.findByName(registeredFacility.getFacilityType());
        if (facilityType == null) {
            missingNinEntityInLocalRepository.save(new MissingNinEntityInLocal(registeredFacility.getFacilityType(), EntityType.FacilityType));
            throw new RuntimeException(String.format("New missing facility type %s", registeredFacility.getFacilityType()));
        }
    }

    private NINResponsePageDTO getNinResponsePageDTO(RestTemplate restTemplate, NinSyncDetails.PageToRequest page) {
        String fullUrl = String.format("https://nin.nhp.gov.in/api/facilities?api-key=%s&fields=%s&updated_on=%s&offset=%s&limit=%d", apiKey, FacilityDownloadService.FIELDS_FOR_FACILITY, page.getDate(), page.getOffset(), PAGE_SIZE);
        logger.info(String.format("Making API call - %s", fullUrl));
        ResponseEntity<NINResponsePageDTO> responseEntity = restTemplate.exchange(fullUrl, HttpMethod.GET, null, NINResponsePageDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT)
            return NINResponsePageDTO.noContent();
        else if (responseEntity.getBody() == null && responseEntity.getStatusCode() == HttpStatus.OK)
            return NINResponsePageDTO.noContent();
//        response = restTemplate.getForObject(fullUrl, NINResponsePageDTO.class);
        return responseEntity.getBody();
    }
}
