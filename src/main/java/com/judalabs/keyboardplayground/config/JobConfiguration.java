package com.judalabs.keyboardplayground.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.judalabs.keyboardplayground.features.metrics.MetricsCollectorService;
import com.judalabs.keyboardplayground.shared.layout.LayoutKey;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private Step loadStep() {
        return new StepBuilder("load", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    final StringBuilder content = readFile();
                    chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("corpus", content.toString());
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    private StringBuilder readFile() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("testcorpus.txt");
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private Step metricsStep() {
        return new StepBuilder("metrics", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    final StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
                    final JobParameters jobParameters = stepExecution.getJobExecution().getJobParameters();
                    final String corpus = stepExecution.getJobExecution().getExecutionContext().getString("corpus");

                    final String inputString = jobParameters.getString("layoutKeys");

                    final List<LayoutKey> layoutKeys = objectMapper.readValue(inputString, new TypeReference<>() {});

                    metricsCollectorService.processMetrics(layoutKeys, corpus);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
