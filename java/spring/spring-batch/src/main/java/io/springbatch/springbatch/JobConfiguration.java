package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {


//    @Bean
//    public Job job(JobRepository jobRepository, Step step1, Step step2){
//        return new JobBuilder("batchJob1", jobRepository)
//                .start(step1)
//                .next(step2)
//                .build();
//    }

    @Bean
    public Job batchJob2(JobRepository jobRepository, Flow flow, Step step5){
        return new JobBuilder("batchJob2", jobRepository)
                .start(flow)
                .next(step5)
                .end()
                .build();
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("===========step1 finished===========");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step2", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("===========step2 finished===========");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }



    @Bean
    public Flow flow(Step step3, Step step4){
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
        flowBuilder.start(step3)
                .next(step4)
                .end();
        return flowBuilder.build();
    }

    @Bean
    public Step step3(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step3", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("===========step3 finished===========");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }

    @Bean
    public Step step4(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step4", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("===========step4 finished===========");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }

    @Bean
    public Step step5(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step5", jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("===========step5 finished===========");
                return RepeatStatus.FINISHED;
            }
        }, platformTransactionManager).build();
    }
}
