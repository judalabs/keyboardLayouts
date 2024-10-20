package com.judalabs.keyboardplayground;

import com.judalabs.keyboardplayground.corpus.LetterCount;
import com.judalabs.keyboardplayground.metrics.ProducesMetrics;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorpusStatsCollector {

    private final Comparator<LetterCount> comparator = Comparator
            .comparingDouble(LetterCount::frequency)
            .reversed();
    private final List<ProducesMetrics> metricProducers;

    private Long words = 0L;
    private Map<Integer, Long> letterCount = new HashMap<>();
    private Long totalLetters = 0L;

    public CorpusStatsCollector(List<ProducesMetrics> producers) {
        metricProducers = producers;
        for (int letter = ' '; letter <= 'z'; letter++) {
            letterCount.put(letter, 0L);
        }
    }

    public void receive(int elem) {
        if (' ' == elem) {
            words++;
            metricProducers.reset();
        } else {
            metricProducers.compute(elem);
        }
        final Long count = letterCount.get(elem) + 1;
        letterCount.put(elem, count);
        totalLetters++;
    }

    public Long getWords() {
        System.out.println(metricProducers.resultAll());
        return words > 0 ? words + 1 : 0;
    }

    public List<LetterCount> getLetterCount() {
        return letterCount.entrySet()
                .stream().filter(entry -> entry.getValue() > 0)
                .map(this::toLetterCount)
                .sorted(comparator)
                .toList();
    }

    private LetterCount toLetterCount(Map.Entry<Integer, Long> entry) {
        final char letter = (char) entry.getKey().intValue();
        final Long countLetter = entry.getValue();

        return new LetterCount(letter, countLetter, (double) countLetter / totalLetters);
    }

    public double sfb() {
        return metricProducers.result(totalLetters);
    }
}
