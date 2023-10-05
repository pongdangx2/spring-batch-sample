package me.lkh.springbatchsample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)	// spring boot가 제공하는 기능, 콘솔에 찍히는 output을 캡처할 수 있다.
class SpringBatchSampleApplicationTests {

	@Autowired
	private Job job;

	@Autowired
	private JobLauncher jobLauncher;

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
