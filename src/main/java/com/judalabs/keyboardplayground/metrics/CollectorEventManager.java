package com.judalabs.keyboardplayground.metrics;

import com.judalabs.keyboardplayground.keyboard.DefaultLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorEventManager {

    private final List<NewLetterListener> newLetterListeners = new ArrayList<>();
    private final List<WordFinishedListener> wordFinishedListeners = new ArrayList<>();

    public CollectorEventManager() {
        final SameFingerBigram sameFingerBigram = new SameFingerBigram(DefaultLayout.build().getKeyCodes());
        newLetterListeners.add(sameFingerBigram);
        wordFinishedListeners.add(sameFingerBigram);
    }

    public void notifyNewWord() {
        wordFinishedListeners.forEach(WordFinishedListener::finished);
    }

    public void compute(int elem) {
        newLetterListeners.forEach(newLetterListener -> newLetterListener.compute(elem));
    }

    public String getResultsFromListeners(Long totalLetters) {
        return newLetterListeners.stream()
                .map(newLetterListener -> newLetterListener.resultFormat(totalLetters))
                .collect(Collectors.joining("\n"));
    }
}
