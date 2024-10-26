package com.judalabs.keyboardplayground.metrics;

import com.judalabs.keyboardplayground.keyboard.Finger;
import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import com.judalabs.keyboardplayground.metrics.impl.DoubleLetterBigram;
import com.judalabs.keyboardplayground.metrics.impl.SameFingerBigram;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class CollectorEventManager {

    private final Map<ObservableType, List<CollectorListener>> eventListMap = new EnumMap<>(ObservableType.class);

    public CollectorEventManager(List<LayoutKey> keyCodes) {
        final Map<Character, Finger> fingerByChar = init(keyCodes);
        final var sfb = new SameFingerBigram(fingerByChar);
        final var doubleLetterBigram = new DoubleLetterBigram(fingerByChar);

        eventListMap.computeIfPresent(ObservableType.ADD, add(List.of(sfb, doubleLetterBigram)));
        eventListMap.computeIfPresent(ObservableType.FINISHED_WORD, add(List.of(sfb, doubleLetterBigram)));
    }

    private Map<Character, Finger> init(List<LayoutKey> keyCodes) {
        eventListMap.put(ObservableType.ADD, new ArrayList<>());
        eventListMap.put(ObservableType.FINISHED_WORD, new ArrayList<>());
        return keyCodes.stream()
                .collect(Collectors.toMap(lk -> lk.keyCode().getNormalChar(), LayoutKey::finger));
    }

    public void notifyNewWord() {
        eventListMap.get(ObservableType.FINISHED_WORD).
                forEach(CollectorListener::finished);
    }

    public void compute(int elem) {
        eventListMap.get(ObservableType.ADD)
                .forEach(newLetterListener -> newLetterListener.compute((char) elem));
    }

    public String getResultsFromListeners(Long totalLetters) {
        return eventListMap.get(ObservableType.FINISHED_WORD).stream()
                .map(newLetterListener -> newLetterListener.resultFormat(totalLetters))
                .collect(Collectors.joining("\n\t","\t", ""));
    }


    private static BiFunction<ObservableType, List<CollectorListener>, List<CollectorListener>> add(
            List<CollectorListener> listeners) {
        return (k, v) -> {
            v.addAll(listeners);
            return v;
        };
    }
}
