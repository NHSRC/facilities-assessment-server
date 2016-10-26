package org.nhsrc;

import org.nhsrc.converter.UUIDConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UUIDConverter());
    }

    public static void main(String[] args) {
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
    }
}
