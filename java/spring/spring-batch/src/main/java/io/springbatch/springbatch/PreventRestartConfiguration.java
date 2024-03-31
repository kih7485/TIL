package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class PreventRestartConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step1, Step step2, Step step3){
        return new JobBuilder("job", jobRepository)
                .start(step1)
                .next(step2)
                .next(step3)
                .preventRestart()
                .build();
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet(((contribution, chunkContext) -> {
            log.info("=======step1========");
            return RepeatStatus.FINISHED;
        }), platformTransactionManager).build();

    }


    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step2", jobRepository).tasklet(((contribution, chunkContext) -> {
            log.info("=======step2========");
            return RepeatStatus.FINISHED;
        }), platformTransactionManager).build();

    }


    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step3", jobRepository).tasklet(((contribution, chunkContext) -> {
            log.info("=======step3========");
            return RepeatStatus.FINISHED;
        }), platformTransactionManager).build();

    }
}