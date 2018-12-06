package org.nhsrc.config;

import org.nhsrc.domain.*;
import org.nhsrc.domain.security.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(AssessmentType.class);
        config.exposeIdsFor(Department.class);
        config.exposeIdsFor(AssessmentToolMode.class);
        config.exposeIdsFor(AssessmentTool.class);

        config.exposeIdsFor(Checklist.class);
        config.exposeIdsFor(AreaOfConcern.class);
        config.exposeIdsFor(Standard.class);
        config.exposeIdsFor(MeasurableElement.class);
        config.exposeIdsFor(Checkpoint.class);
        config.exposeIdsFor(IndicatorDefinition.class);
        config.exposeIdsFor(State.class);
        config.exposeIdsFor(District.class);
        config.exposeIdsFor(Facility.class);
        config.exposeIdsFor(FacilityType.class);

        config.exposeIdsFor(FacilityAssessment.class);
        config.exposeIdsFor(CheckpointScore.class);
        config.exposeIdsFor(Indicator.class);

        config.exposeIdsFor(User.class);
    }
}