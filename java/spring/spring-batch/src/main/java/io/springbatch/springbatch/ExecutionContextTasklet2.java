package io.springbatch.springbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ExecutionContextTasklet2 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("contextTasklet2");

        ExecutionContext jobExecutionContext =
                contribution
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        ExecutionContext stepExecutionContext =
                contribution
                .getStepExecution()
                .getExecutionContext();

        log.info("jobName={}, stepName={}",
                jobExecutionContext.get("jobName"),
                stepExecutionContext.get("stepName"));
        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
        if(stepExecutionContext.get("stepName") == null){
            stepExecutionContext.put("stepName", stepName);
        }

        return RepeatStatus.FINISHED;
    }
}
