package com.judalabs.keyboardplayground;

import com.judalabs.keyboardplayground.corpus.LetterCount;
import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import com.judalabs.keyboardplayground.metrics.CollectorEventManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorpusStatsCollector {

    private final CollectorEventManager collectorEventManager;

    private Long words = 0L;
    private final Map<Integer, Long> letterCount = new HashMap<>();
    private Long totalLetters = 0L;

    public CorpusStatsCollector(List<LayoutKey> keyCodes) {
        collectorEventManager = new CollectorEventManager(keyCodes);
        for (int letter = ' '; letter <= 'z'; letter++) {
            letterCount.put(letter, 0L);
        }
    }

    public void receive(int elem) {
        if (' ' == elem) {
            words++;
            collectorEventManager.notifyNewWord();
        } else {
            collectorEventManager.compute(elem);
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
        return collectorEventManager.getResultsFromListeners(totalLetters);
    }
}
