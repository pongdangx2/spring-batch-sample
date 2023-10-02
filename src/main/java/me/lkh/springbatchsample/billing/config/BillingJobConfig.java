package me.lkh.springbatchsample.billing.config;

import me.lkh.springbatchsample.billing.job.BillingJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1. Spring Batch 설정은 애플리케이션의 설정과 분리되어 있어야 합니다.
 * 2. 이 설정파일에서는 Job, Step 등 Spring Batch와 연관된 빈이 등록됩니다.
 */
@Configuration
public class BillingJobConfig {
    // Job Definition 작성

    // jobRepository 빈이 없다고 나오는데, 이유를 모르겠음.
    @Bean
    public Job job(JobRepository jobRepository) {
        return new BillingJob(jobRepository);
    }
}
