package org.nhsrc;

import org.nhsrc.config.DatabaseConfiguration;
import org.nhsrc.config.LengthInterceptor;
import org.nhsrc.config.RestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.TimeZone;

@SpringBootApplication
@Import({RestConfiguration.class, DatabaseConfiguration.class})
@EnableJpaAuditing
public class FacilitiesAssessmentServerApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LengthInterceptor()).addPathPatterns("/**");
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Calcutta"));
        SpringApplication.run(FacilitiesAssessmentServerApplication.class, args);
    }
}
