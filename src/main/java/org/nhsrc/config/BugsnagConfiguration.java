package org.nhsrc.config;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfiguration {

    @Value("${bugsnag.apiKey}")
    private String apiKey;

    @Value("${bugsnag.releaseStage}")
    private String releaseStage;

    @Value("${bugsnag.notifyReleaseStages}")
    private String[] notifyReleaseStages;

    @Value("${bugsnag.implementationName}")
    private String implementationName;

    @Bean
    public Bugsnag bugsnag() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info(String.format("Bugsnag 'apiKey' = %s", apiKey));
        logger.info(String.format("Bugsnag 'releaseStage' = %s", releaseStage));
        logger.info(String.format("Bugsnag 'implementationName' = %s", implementationName));

        Bugsnag _bugsnag = new Bugsnag(apiKey, false);
        _bugsnag.setReleaseStage(releaseStage);
        _bugsnag.setNotifyReleaseStages(notifyReleaseStages);
        _bugsnag.addCallback((report) -> report.setAppInfo("Implementation", implementationName));
        return _bugsnag;
    }
}
