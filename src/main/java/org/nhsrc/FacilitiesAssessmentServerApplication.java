package org.nhsrc;

import org.nhsrc.config.DatabaseConfiguration;
import org.nhsrc.config.RestConfiguration;
import org.nhsrc.domain.*;
import org.nhsrc.repository.AssessmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.util.TimeZone;
import java.util.stream.Collectors;

@SpringBootApplication
@Import({RestConfiguration.class, DatabaseConfiguration.class})
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {
    @Autowired
    private AssessmentTypeRepository assessmentTypeRepository;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ext/**").addResourceLocations(String.format("file:///%s/", new File("external").getAbsolutePath()));
    }

    @Bean
    public ResourceProcessor<Resource<Facility>> facilityProcessor() {
        return new ResourceProcessor<Resource<Facility>>() {
            @Override
            public Resource<Facility> process(Resource<Facility> resource) {
                Facility facility = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(facility.getFacilityType().getUuid().toString(), "facilityTypeUUID"));
                resource.add(new Link(facility.getDistrict().getUuid().toString(), "districtUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<District>> districtProcessor() {
        return new ResourceProcessor<Resource<District>>() {
            @Override
            public Resource<District> process(Resource<District> resource) {
                District district = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(district.getState().getUuid().toString(), "stateUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<Standard>> standardProcessor() {
        return new ResourceProcessor<Resource<Standard>>() {
            @Override
            public Resource<Standard> process(Resource<Standard> resource) {
                Standard standard = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(standard.getAreaOfConcern().getUuid().toString(), "areaOfConcernUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<MeasurableElement>> measurableElementProcessor() {
        return new ResourceProcessor<Resource<MeasurableElement>>() {
            @Override
            public Resource<MeasurableElement> process(Resource<MeasurableElement> resource) {
                MeasurableElement measurableElement = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(measurableElement.getStandard().getUuid().toString(), "standardUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<Checkpoint>> checkpointProcessor() {
        return new ResourceProcessor<Resource<Checkpoint>>() {
            @Override
            public Resource<Checkpoint> process(Resource<Checkpoint> resource) {
                Checkpoint checkpoint = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checkpoint.getChecklist().getUuid().toString(), "checklistUUID"));
                resource.add(new Link(checkpoint.getMeasurableElement().getUuid().toString(), "measurableElementUUID"));
                if (checkpoint.getState() != null)
                    resource.add(new Link(checkpoint.getState().getUuid().toString(), "stateUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<Checklist>> checklistProcessor() {
        return new ResourceProcessor<Resource<Checklist>>() {
            @Override
            public Resource<Checklist> process(Resource<Checklist> resource) {
                Checklist checklist = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checklist.getDepartment().getUuid().toString(), "departmentUUID"));
                resource.add(new Link(checklist.getAssessmentTool().getUuid().toString(), "assessmentToolUUID"));
                resource.add(checklist.getAreasOfConcern().stream().map(aoc -> new Link(aoc.getUuid().toString(), "areasOfConcernUUIDs")).collect(Collectors.toList()));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<FacilityAssessment>> facilityAssessmentProcessor() {
        return new ResourceProcessor<Resource<FacilityAssessment>>() {
            @Override
            public Resource<FacilityAssessment> process(Resource<FacilityAssessment> resource) {
                FacilityAssessment facilityAssessment = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(facilityAssessment.getFacility().getUuid().toString(), "facilityUUID"));
                resource.add(new Link(facilityAssessment.getAssessmentTool().getUuid().toString(), "assessmentToolUUID"));
                AssessmentType assessmentType = assessmentTypeRepository.findByName(AssessmentType.EXTERNAL);
                resource.add(new Link(assessmentType.getUuid().toString(), "assessmentTypeUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<CheckpointScore>> checkpointScoreProcessor() {
        return new ResourceProcessor<Resource<CheckpointScore>>() {
            @Override
            public Resource<CheckpointScore> process(Resource<CheckpointScore> resource) {
                CheckpointScore checkpointScore = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checkpointScore.getFacilityAssessment().getUuid().toString(), "facilityAssessmentUUID"));
                resource.add(new Link(checkpointScore.getChecklist().getUuid().toString(), "checklistUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getUuid().toString(), "checkpointUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getUuid().toString(), "areaOfConcernUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getUuid().toString(), "standardUUID"));
                return resource;
            }
        };
    }
}
