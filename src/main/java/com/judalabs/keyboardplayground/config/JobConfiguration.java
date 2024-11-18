package com.judalabs.keyboardplayground.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.judalabs.keyboardplayground.features.metrics.MetricsCollectorService;
import com.judalabs.keyboardplayground.shared.layout.LayoutKey;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final ObjectMapper objectMapper;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MetricsCollectorService metricsCollectorService;

    @Bean
    public Job full() {
        return new JobBuilder("full", jobRepository)
                .start(loadStep())
                .next(metricsStep())
                .build();
    }

    private Step metricsStep() {
        return new StepBuilder("metrics", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    final JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
                    final String inputString = jobParameters.getString("layoutKeys");
                    final List<LayoutKey> layoutKeys = objectMapper.readValue(inputString, new TypeReference<>() {});
                    metricsCollectorService.processMetrics(layoutKeys);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    private Step loadStep() {
        return new StepBuilder("load", jobRepository)
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED, transactionManager)
                .build();
    }
}
