package com.judalabs.keyboardplayground.features.metrics;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MetricsController {

    private final StatsCollectorService statsCollectorService;

    @GetMapping
    public void processMetrics() {
        statsCollectorService.processMetrics();
    }
}
