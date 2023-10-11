package me.lkh.springbatchsample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBatchTest	// JobLauncherTestUtils, JobRepositoryTestUtils를 빈으로 등록해줍니다.
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)	// spring boot가 제공하는 기능, 콘솔에 찍히는 output을 캡처할 수 있습니다.
class SpringBatchSampleApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;	// test application context에서 각각의 Job을 자동으로 탐지합니다.

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	/*
	@Autowired
	private Job job;

	@Autowired
	private JobLauncher jobLauncher;
	*/

	@BeforeEach
	public void setUp() {
		this.jobRepositoryTestUtils.removeJobExecutions();	// 각각의 테스트별로 JobExecution을 정리합니다.
															// 이를 통해 여러번 테스트를 수행해도 Job Instance가 이미 존재해서 테스트가 실패하는 일을 막을 수 있습니다.
	}

	@Test
	void testJobExecution() throws Exception {
		// given
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", "src/main/resources/billing-2023-01.csv")
				.toJobParameters();

		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

		// then
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
		Assertions.assertTrue(Files.exists(Paths.get("staging", "billing-2023-01.csv")));
	}

	/*
	// test v3
	@Test
	void testJobExecution(CapturedOutput output) throws Exception {
		// given
		String parameterFileName = "/some/input/file";
		JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParametersBuilder()
				.addString("input.file", parameterFileName)
				.toJobParameters();

		// when
		JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

		// then
		Assertions.assertTrue(output.getOut().contains("processing billing information from file " + parameterFileName));
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
	 */

	/*
	// test version 2
	@Test
	void testJobExecution(CapturedOutput output) throws Exception {
		// given
		String parameterFileName = "/some/input/file";
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("input.file", parameterFileName)
				.addString("file.format", "csv", false)
				.toJobParameters();

		// when
		JobExecution jobExecution = this.jobLauncher.run(this.job, jobParameters);

		// then
		Assertions.assertTrue(output.getOut().contains("processing billing information from file " + parameterFileName));
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
*/

	/*
	// test version 1
	@Test
	void testJobExecution(CapturedOutput output) throws Exception {
		// given
		JobParameters jobParameters = new JobParameters();

		// when
		JobExecution jobExecution = this.jobLauncher.run(this.job, jobParameters);

		// then
		Assertions.assertTrue(output.getOut().contains("processing billing information"));
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
	 */

}
