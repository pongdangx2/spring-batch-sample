package me.lkh.springbatchsample.billing.config;

import me.lkh.springbatchsample.billing.job.BillingJob;
import me.lkh.springbatchsample.billing.tasklet.FilePreparationTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

/**
 * 1. Spring Batch 설정은 애플리케이션의 설정과 분리되어 있어야 합니다.
 * 2. 이 설정파일에서는 Job, Step 등 Spring Batch와 연관된 빈이 등록됩니다.
 */
@Configuration
public class BillingJobConfig {
    // Job Definition 작성

    /*
    // Job v1
    @Bean
    public Job job(JobRepository jobRepository) {
        return new BillingJob(jobRepository);
    }
     */

    // Job v2
    @Bean
    public Job job(JobRepository jobRepository, Step step1){
        return new JobBuilder("BillingJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        return new StepBuilder("filePreparation", jobRepository)
                .tasklet(new FilePreparationTasklet(), transactionManager)
                .build();
    }
}
