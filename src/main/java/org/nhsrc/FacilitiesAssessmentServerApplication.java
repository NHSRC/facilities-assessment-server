package org.nhsrc;

import org.nhsrc.config.DatabaseConfiguration;
import org.nhsrc.config.RestConfiguration;
import org.nhsrc.domain.District;
import org.nhsrc.domain.Facility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.TimeZone;

@SpringBootApplication
@Import({RestConfiguration.class, DatabaseConfiguration.class})
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
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
}
