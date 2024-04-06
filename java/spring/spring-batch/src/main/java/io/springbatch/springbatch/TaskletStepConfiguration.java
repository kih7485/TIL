package io.springbatch.springbatch;

import io.springbatch.springbatch.tasklet.CutsomTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class TaskletStepConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, Step step2){
        return new JobBuilder("batchJob", jobRepository)
                .start(step2)
                .build();
    }


    @Bean
    public Step taskStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("=======taskStep========");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(10, platformTransactionManager)
                .reader(new ListItemReader<>(Arrays.asList("item1","item2","item3","item4","item5")))
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        return item.toUpperCase();
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> items) throws Exception {
                        items.forEach(item -> log.info(item));
                    }
                }).build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("step1", jobRepository)
                .tasklet(new CutsomTasklet(), platformTransactionManager)
                .build();
    }
}