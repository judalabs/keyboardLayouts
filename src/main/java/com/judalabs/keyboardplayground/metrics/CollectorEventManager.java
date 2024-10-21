package com.judalabs.keyboardplayground.metrics;

import com.judalabs.keyboardplayground.keyboard.DefaultLayout;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CollectorEventManager {

    private final Map<EnumEvent, List<CollectorListener>> eventListMap = new EnumMap<>(EnumEvent.class);

    public CollectorEventManager() {
        final var sfb = new SameFingerBigram(DefaultLayout.build().getKeyCodes());
        final var doubleLetterBigram = new DoubleLetterBigram(DefaultLayout.build().getKeyCodes());
        eventListMap.put(EnumEvent.ADD, new ArrayList<>());
        eventListMap.put(EnumEvent.FINISHED_WORD, new ArrayList<>());
        eventListMap.computeIfPresent(EnumEvent.ADD, add(List.of(sfb, doubleLetterBigram)));
        eventListMap.computeIfPresent(EnumEvent.FINISHED_WORD, add(List.of(sfb, doubleLetterBigram)));
    }

    public void notifyNewWord() {
        eventListMap.get(EnumEvent.FINISHED_WORD).
                forEach(CollectorListener::finished);
    }

    public void compute(int elem) {
        eventListMap.get(EnumEvent.ADD)
                .forEach(newLetterListener -> newLetterListener.compute(elem));
    }

    public String getResultsFromListeners(Long totalLetters) {
        return eventListMap.get(EnumEvent.FINISHED_WORD).stream()
                .map(newLetterListener -> newLetterListener.resultFormat(totalLetters))
                .collect(Collectors.joining("\n\t","\t", ""));
    }


    private static BiFunction<EnumEvent, List<CollectorListener>, List<CollectorListener>> add(
            List<CollectorListener> listeners) {
        return (k, v) -> {
            v.addAll(listeners);
            return v;
        };
    }
}
