package me.lkh.springbatchsample.billing.job;

import org.springframework.batch.core.*;
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

    /*
    // 1. Job and Job Execution
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
     */

    // 2. Job Instance and Job Parameter
    //    1) $ ./mvnw package -Dmaven.test.skip=true
    //    2) $ java -jar target/spring-batch-sample-0.0.1-SNAPSHOT.jar input.file=src/main/resources/test-file.csv
    //      --> processing billing information from file src/main/resources/test-file.csv 출력
    //    3) select * from BATCH_JOB_INSTANCE; 로 Job Instance 메타데이터 조회
    //      --> JOB_KEY 컬럼: identifying Job Parameter 의 Hash 값
    //    4) 성공한 Job parameter 로 또다시 실행하면 Job Execution이 생기지 않고 IllegalStateException이 발생합니다.
    //       즉, 같은 명령어
    //          $ java -jar target/spring-batch-sample-0.0.1-SNAPSHOT.jar input.file=src/main/resources/test-file.csv
    //       를 또다시 실행하면 IllegalStateException이 발생합니다.

    @Override
    public void execute(JobExecution execution) {
        JobParameters jobParameters = execution.getJobParameters();
        String inputFile = jobParameters.getString("input.file");
        System.out.println("processing billing information from file " + inputFile);
        execution.setStatus(BatchStatus.COMPLETED);
        execution.setExitStatus(ExitStatus.COMPLETED);
        this.jobRepository.update(execution);
    }
}
