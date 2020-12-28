package org.nhsrc.config.quartz;

import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
import org.nhsrc.service.FacilityDownloadService;
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
    @Value("${cron.assessmentScoring}")
    private String cronExpression;
    private static Logger logger = LoggerFactory.getLogger(BackgroundJob.class);
    private static List<GrantedAuthority> backgroundJobAuthorities;

    static {
        backgroundJobAuthorities = Privilege.createAuthorities(Privilege.USER.getSpringName(), Privilege.FACILITY_WRITE.getSpringName(), Privilege.ASSESSMENT_READ.getSpringName(), Privilege.ASSESSMENT_WRITE.getSpringName());
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("Starting job.", cronExpression);

        Authentication auth = new UsernamePasswordAuthenticationToken(User.BACKGROUND_SERVICE_USER_EMAIL, "", backgroundJobAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        scoringService.scoreAssessments();
        logger.info("Completed scoring assessments.");
        facilityDownloadService.download();
        logger.info("Completed download and job.");
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