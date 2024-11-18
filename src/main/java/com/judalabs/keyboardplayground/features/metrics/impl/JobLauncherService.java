package com.judalabs.keyboardplayground.features.metrics.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judalabs.keyboardplayground.shared.layout.DefaultLayout;
import com.judalabs.keyboardplayground.shared.layout.KeyboardLayout;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobLauncherService {

    private final Job job;
    private final JobLauncher jobLauncher;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void execute(KeyboardLayout keyboardLayout) {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("layoutKeys", objectMapper.writeValueAsString(keyboardLayout.getKeyCodes()))
                .toJobParameters();

        final JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        if (jobExecution.getStatus() != BatchStatus.COMPLETED){
            throw new JobNotCompletedException();
        }
    }

    public void executeDefaultKeyCodes() {
        execute(DefaultLayout.build());
    }
}
