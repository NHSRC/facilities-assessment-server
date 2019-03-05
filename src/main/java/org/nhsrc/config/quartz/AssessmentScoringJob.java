package org.nhsrc.config.quartz;

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
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class AssessmentScoringJob implements Job {
    private final ScoringService scoringService;
    @Value("${cron.assessmentScoring}")
    private String cronExpression;
    private static Logger logger = LoggerFactory.getLogger(AssessmentScoringJob.class);

    @Autowired
    public AssessmentScoringJob(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("Starting job.", cronExpression);
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