package org.nhsrc;

import org.nhsrc.config.DatabaseConfiguration;
import org.nhsrc.config.RestConfiguration;
import org.nhsrc.config.SecurityConfiguration;
import org.nhsrc.domain.*;
import org.nhsrc.domain.assessment.AssessmentCustomInfo;
import org.nhsrc.domain.assessment.FacilityAssessment;
import org.nhsrc.web.framework.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.io.File;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Import({RestConfiguration.class, DatabaseConfiguration.class, SecurityConfiguration.class})
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {
    private final RequestInterceptor requestInterceptor;
    private static Logger logger = LoggerFactory.getLogger(FacilitiesAssessmentServerApplication.class);

    private final String[] interceptedPathList = Stream.of(
            "district",
            "facility"
    ).map(path-> "/api/" + path + "/**").toArray(String[]::new);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        logger.info("Server Starting...");
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
    }

    @Autowired
    public FacilitiesAssessmentServerApplication(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/dashboard").setViewName("forward:/dashboard/index.html");
        registerDashboardPaths(registry, "account");
        registerDashboardPaths(registry, "facilityView");
        registerDashboardPaths(registry, "settings");
        registerDashboardPaths(registry, "login");
        registerDashboardPaths(registry, "register");
        registerDashboardPaths(registry, "404");
        registerDashboardPaths(registry, "noStateAccess");
    }

    private void registerDashboardPaths(ViewControllerRegistry registry, String subPath) {
        registry.addViewController(String.format("/dashboard/%s", subPath)).setViewName(String.format("forward:/dashboard/index.html#/%s",subPath));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ext/**").addResourceLocations(String.format("file:///%s/", new File("external").getAbsolutePath()));
        registry.addResourceHandler("/app/**").addResourceLocations(String.format("file:///%s/", new File("app").getAbsolutePath()));
        registry.addResourceHandler("/dashboard/**").addResourceLocations(String.format("file:///%s/", new File("dashboard").getAbsolutePath()));
    }

    @Bean
    @Deprecated // "For backward compatibility"
    public ResourceProcessor<Resource<Checklist>> checklistProcessor() {
        return new ResourceProcessor<Resource<Checklist>>() {
            @Override
            public Resource<Checklist> process(Resource<Checklist> resource) {
                Checklist checklist = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checklist.getDepartment().getUuid().toString(), "departmentUUID"));
                if (checklist.getState() != null)
                    resource.add(new Link(checklist.getState().getUuid().toString(), "stateUUID"));
                resource.add(checklist.getAssessmentTools().stream().map(at -> new Link(at.getUuid().toString(), "assessmentToolUUIDs")).collect(Collectors.toList()));
                resource.add(checklist.getAreasOfConcern().stream().map(aoc -> new Link(aoc.getUuid().toString(), "areasOfConcernUUIDs")).collect(Collectors.toList()));
                return resource;
            }
        };
    }

    @Bean
    @Deprecated // "For backward compatibility"
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
    @Deprecated // "For backward compatibility"
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
    @Deprecated // "For backward compatibility"
    public ResourceProcessor<Resource<Checkpoint>> checkpointProcessor() {
        return new ResourceProcessor<Resource<Checkpoint>>() {
            @Override
            public Resource<Checkpoint> process(Resource<Checkpoint> resource) {
                Checkpoint checkpoint = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checkpoint.getChecklist().getUuid().toString(), "checklistUUID"));
                resource.add(new Link(checkpoint.getMeasurableElement().getUuid().toString(), "measurableElementUUID"));
                return resource;
            }
        };
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
    public ResourceProcessor<Resource<FacilityAssessment>> facilityAssessmentProcessor() {
        return new ResourceProcessor<Resource<FacilityAssessment>>() {
            @Override
            public Resource<FacilityAssessment> process(Resource<FacilityAssessment> resource) {
                FacilityAssessment facilityAssessment = resource.getContent();
                resource.removeLinks();

                addResourceLink(resource, facilityAssessment.getFacility(), "facilityUUID");
                addResourceLink(resource, facilityAssessment.getState(), "stateUUID");
                addResourceLink(resource, facilityAssessment.getDistrict(), "districtUUID");
                addResourceLink(resource, facilityAssessment.getFacilityType(), "facilityTypeUUID");
                resource.add(new Link(facilityAssessment.getAssessmentTool().getUuid().toString(), "assessmentToolUUID"));
                resource.add(new Link(facilityAssessment.getAssessmentType().getUuid().toString(), "assessmentTypeUUID"));
                return resource;
            }
        };
    }

    private void addResourceLink(Resource<FacilityAssessment> resource, AbstractEntity abstractEntity, String linkName) {
        if (abstractEntity == null) return;
        resource.add(new Link(abstractEntity.getUuid().toString(), linkName));
    }

    @Bean
    public ResourceProcessor<Resource<CheckpointScore>> checkpointScoreProcessor() {
        return new ResourceProcessor<Resource<CheckpointScore>>() {
            @Override
            public Resource<CheckpointScore> process(Resource<CheckpointScore> resource) {
                CheckpointScore checkpointScore = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(checkpointScore.getFacilityAssessment().getUuidString(), "facilityAssessmentUUID"));
                resource.add(new Link(checkpointScore.getChecklist().getUuid().toString(), "checklistUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getUuid().toString(), "checkpointUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getAreaOfConcern().getUuid().toString(), "areaOfConcernUUID"));
                resource.add(new Link(checkpointScore.getCheckpoint().getMeasurableElement().getStandard().getUuid().toString(), "standardUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<IndicatorDefinition>> indicatorDefinitionProcessor() {
        return new ResourceProcessor<Resource<IndicatorDefinition>>() {
            @Override
            public Resource<IndicatorDefinition> process(Resource<IndicatorDefinition> resource) {
                IndicatorDefinition indicatorDefinition = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(indicatorDefinition.getAssessmentTool().getUuid().toString(), "assessmentToolUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<Indicator>> indicatorProcessor() {
        return new ResourceProcessor<Resource<Indicator>>() {
            @Override
            public Resource<Indicator> process(Resource<Indicator> resource) {
                Indicator indicator = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(indicator.getFacilityAssessment().getUuidString(), "facilityAssessmentUUID"));
                resource.add(new Link(indicator.getIndicatorDefinition().getUuidString(), "indicatorDefinitionUUID"));
                return resource;
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<ExcludedAssessmentToolState>> excludedAssessmentToolStateProcessor() {
        return new ResourceProcessor<Resource<ExcludedAssessmentToolState>>() {
            @Override
            public Resource<ExcludedAssessmentToolState> process(Resource<ExcludedAssessmentToolState> resource) {
                ExcludedAssessmentToolState excludedCheckpointState = resource.getContent();
                resource.removeLinks();
                resource.add(new Link(excludedCheckpointState.getState().getUuidString(), "stateUUID"));
                resource.add(new Link(excludedCheckpointState.getAssessmentTool().getUuidString(), "assessmentToolUUID"));
                return resource;
            }
        };
    }

    @Bean("mappedRequestInterceptor")
    public MappedInterceptor mappedTransactionalResourceInterceptor() {
        return new MappedInterceptor(interceptedPathList, requestInterceptor);
    }
}
