package me.lkh.springbatchsample.billing.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobRepository;

/**
 * Job
 *  - Job은 자신의 상태(status)와 종료 코드 (exit code)를 Job Repository에 전달해야한다.
 *  - 이게 제대로 되지 않으면, STARTING [status]와 UNKNOWN [exit code]가 BATCH_JOB_EXECUTION 테이블의 status/exit code 테이블에 저장된다.
 */
public class BillingJob implements Job {

    private JobRepository jobRepository;

    public BillingJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public String getName() {
        return "billing-job";
    }

    @Override
    public void execute(JobExecution execution) {
        // execute() 에서는 예외를 던지면 안되기 때문에, 모든 예외는 무조건 처리되어야 합니다.
        try{
            System.out.println("processing billing information");
//            throw new Exception("Unable to process billing information");
            execution.setStatus(BatchStatus.COMPLETED);
            execution.setExitStatus(ExitStatus.COMPLETED);
        } catch (Exception exception){
            execution.addFailureException(exception);
            execution.setStatus(BatchStatus.COMPLETED);
            execution.setExitStatus(ExitStatus.FAILED.addExitDescription(exception.getMessage()));
        } finally{
            this.jobRepository.update(execution);
        }
    }
}
