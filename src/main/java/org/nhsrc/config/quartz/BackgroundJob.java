package org.nhsrc.config.quartz;

import com.bugsnag.Bugsnag;
import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.service.FacilityDownloadService;
import org.nhsrc.service.HealthCheckService;
import org.nhsrc.service.ScoringService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DisallowConcurrentExecution
public class BackgroundJob implements Job {
    //    Quartz cannot instantiate if auto-wired via constructor
    @Autowired
    private ScoringService scoringService;
    @Autowired
    private FacilityDownloadService facilityDownloadService;
    @Autowired
    private HealthCheckService healthCheckService;
    @Autowired
    private Bugsnag bugsnag;

    @Value("${cron.main}")
    private String cronExpression;

    @Value("${facility.download.job.enabled}")
    private boolean facilityDownloadJobEnabled;

    private static final Logger logger = LoggerFactory.getLogger(BackgroundJob.class);
    private static final List<GrantedAuthority> backgroundJobAuthorities;

    static {
        backgroundJobAuthorities = Privilege.createAuthorities(Privilege.USER.getSpringName(), Privilege.FACILITY_WRITE.getSpringName(), Privilege.ASSESSMENT_READ.getSpringName(), Privilege.ASSESSMENT_WRITE.getSpringName());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            logger.info(String.format("Starting job. %s", cronExpression));

            Authentication auth = new UsernamePasswordAuthenticationToken(User.BACKGROUND_SERVICE_USER_EMAIL, "", backgroundJobAuthorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            scoringService.scoreAssessments();
            healthCheckService.verifyScoringJob();
            logger.info("Completed scoring assessments.");
            if (facilityDownloadJobEnabled) {
                logger.info("Starting facility download job.");
                facilityDownloadService.download();
                logger.info("Completed facility download job.");
            } else {
                logger.info("Facility download job disabled.");
            }
            healthCheckService.verifyMainJob();
        } catch (Exception e) {
            bugsnag.notify(e);
            throw e;
        }
    }

    @Bean(name = "jobWithCronTriggerBean")
    public JobDetailFactoryBean createJob() {
        return QuartzConfiguration.createJobDetailFactory(this.getClass());
    }

    @Bean(name = "jobWithCronTriggerBeanTrigger")
    public CronTriggerFactoryBean createJobTriggerFactory(@Qualifier("jobWithCronTriggerBean") JobDetail jobDetail) {
        return QuartzConfiguration.createCronTrigger(jobDetail, cronExpression);
    }
}
