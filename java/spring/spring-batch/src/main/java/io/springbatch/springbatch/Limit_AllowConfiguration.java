package io.springbatch.springbatch;

import io.springbatch.springbatch.tasklet.CutsomTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Limit_AllowConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step1, Step step2){
        return new JobBuilder("batchJob", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("=======step1========");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("=======step2========");
                    throw new RuntimeException("step2 was failed");
//                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .startLimit(3)
                .build();
    }
}