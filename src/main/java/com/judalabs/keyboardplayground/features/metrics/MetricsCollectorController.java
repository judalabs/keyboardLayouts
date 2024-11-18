package com.judalabs.keyboardplayground.features.metrics;

import com.judalabs.keyboardplayground.features.metrics.impl.JobLauncherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/metrics")
public class MetricsCollectorController {

    private final JobLauncherService jobLauncherService;

    @GetMapping("/default")
    public void defaultMetrics() {
        jobLauncherService.executeDefaultKeyCodes();
    }
}
