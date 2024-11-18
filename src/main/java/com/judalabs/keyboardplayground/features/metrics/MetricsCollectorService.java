package com.judalabs.keyboardplayground.features.metrics;

import com.judalabs.keyboardplayground.features.metrics.impl.KeyboardLayoutSupplier;
import com.judalabs.keyboardplayground.features.metrics.impl.collector.CorpusStatsCollector;
import com.judalabs.keyboardplayground.features.metrics.impl.collector.EventCollector;
import com.judalabs.keyboardplayground.shared.corpus.CorpusStats;
import com.judalabs.keyboardplayground.shared.corpus.Language;
import com.judalabs.keyboardplayground.shared.layout.LayoutKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MetricsCollectorService {

    private final KeyboardLayoutSupplier keyboardLayoutSupplier;
    private final EventCollector eventCollector;

    public void processMetrics(List<LayoutKey> layoutKeys, String corpus) {
        keyboardLayoutSupplier.init(layoutKeys);

        final CorpusStatsCollector statsCollector = corpus.chars()
                .boxed()
                .collect(new CorpusStatsCollector(eventCollector, ' '));

        final CorpusStats stats = CorpusStats.builder()
                .language(Language.ENGLISH)
                .words(statsCollector.getWords())
                .letterCounts(statsCollector.getLetterCount())
                .results(statsCollector.getResults())
                .build();

        log.info(stats.toString());
    }
}
