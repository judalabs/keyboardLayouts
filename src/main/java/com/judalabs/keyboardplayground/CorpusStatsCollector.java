package com.judalabs.keyboardplayground;

import com.judalabs.keyboardplayground.features.metrics.EventCollector;
import com.judalabs.keyboardplayground.shared.corpus.LetterCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CorpusStatsCollector implements Collector<Integer, CorpusStatsCollector, CorpusStatsCollector> {

    private final EventCollector eventCollector;

    private Long words = 0L;
    private final Map<Integer, Long> letterCount = new HashMap<>();
    private Long totalLetters = 0L;

    public CorpusStatsCollector(EventCollector eventCollector) {
        this.eventCollector = eventCollector;
        for (int letter = ' '; letter <= 'z'; letter++) {
            letterCount.put(letter, 0L);
        }
    }

    public void receive(int elem) {
        if (' ' == elem) {
            words++;
            eventCollector.notifyNewWord();
        } else {
            eventCollector.compute(elem);
        }
        final Long count = letterCount.get(elem) + 1;
        letterCount.put(elem, count);
        totalLetters++;
    }

    public Long getWords() {
        return words > 0 ? words + 1 : 0;
    }

    public List<LetterCount> getLetterCount() {
        return letterCount.entrySet()
                .stream().filter(entry -> entry.getValue() > 0)
                .map(this::toLetterCount)
                .sorted()
                .toList();
    }

    private LetterCount toLetterCount(Map.Entry<Integer, Long> entry) {
        final char letter = (char) entry.getKey().intValue();
        final Long countLetter = entry.getValue();

        return new LetterCount(letter, countLetter, (double) countLetter / totalLetters * 100);
    }

    public String getResults() {
        return eventCollector.getResultsFromListeners(totalLetters);
    }

    @Override
    public Supplier<CorpusStatsCollector> supplier() {
        return ()-> new CorpusStatsCollector(eventCollector);
    }

    @Override
    public BiConsumer<CorpusStatsCollector, Integer> accumulator() {
        return CorpusStatsCollector::receive;
    }

    @Override
    public BinaryOperator<CorpusStatsCollector> combiner() {
        return null;
    }

    @Override
    public Function<CorpusStatsCollector, CorpusStatsCollector> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
