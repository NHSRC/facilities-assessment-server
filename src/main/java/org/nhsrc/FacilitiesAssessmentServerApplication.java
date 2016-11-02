package org.nhsrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
    }
}
