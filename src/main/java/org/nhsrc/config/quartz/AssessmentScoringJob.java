package org.nhsrc.config.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class AssessmentScoringJob implements Job {
    @Value("${cron.assessmentScoring}")
    private String cronExpression;

    private static Logger logger = LoggerFactory.getLogger(AssessmentScoringJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("Running JobWithCronTrigger | cronExpression {}", cronExpression);
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