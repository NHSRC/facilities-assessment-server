package org.nhsrc.config.quartz;

import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.User;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@DisallowConcurrentExecution
public class AssessmentScoringJob implements Job {
    //    Quartz cannot instantiate if auto-wired via constructor
    @Autowired
    private ScoringService scoringService;
    @Value("${cron.assessmentScoring}")
    private String cronExpression;
    private static Logger logger = LoggerFactory.getLogger(AssessmentScoringJob.class);
    private static List<GrantedAuthority> backgroundJobAuthorities;

    static {
        backgroundJobAuthorities = new ArrayList<>();
        backgroundJobAuthorities.add(new SimpleGrantedAuthority(Privilege.USER));
        backgroundJobAuthorities.add(new SimpleGrantedAuthority(Privilege.ASSESSMENT_READ));
        backgroundJobAuthorities.add(new SimpleGrantedAuthority(Privilege.ASSESSMENT_WRITE));
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("Starting job.", cronExpression);

        Authentication auth = new UsernamePasswordAuthenticationToken(User.BACKGROUND_SERVICE_USERS_EMAIL, "", backgroundJobAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        scoringService.scoreAssessments();
        logger.info("Completed Scoring");
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