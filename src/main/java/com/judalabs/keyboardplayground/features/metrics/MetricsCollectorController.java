package com.judalabs.keyboardplayground.features.metrics;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/metrics")
public class MetricsCollectorController {

    private final MetricsCollectorService metricsCollectorService;

    @GetMapping
    public void processMetrics() {
        metricsCollectorService.processMetrics();
    }
}
