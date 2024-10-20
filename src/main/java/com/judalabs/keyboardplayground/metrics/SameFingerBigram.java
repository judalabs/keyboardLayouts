package com.judalabs.keyboardplayground.metrics;

import com.judalabs.keyboardplayground.keyboard.Finger;
import com.judalabs.keyboardplayground.keyboard.LayoutKey;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SameFingerBigram implements ProducesMetrics {

    private final Map<Character, Finger> sfbCandidates;
    private final Map<String, Long> bigramsFound = new HashMap<>();
    private Character currentChar = null;
    private Finger currentFinger = null;

    public SameFingerBigram(List<LayoutKey> layoutKeys) {
        sfbCandidates = layoutKeys.stream()
                .collect(Collectors.toMap(lk -> lk.keyCode().getNormalChar(), LayoutKey::finger));
    }

    public void reset() {
        currentChar = null;
        currentFinger = null;
    }

    public void compute(int newChar) {
        if (currentChar != null) {
            final Finger newFinger = sfbCandidates.get((char) newChar);
            if (currentFinger.equals(newFinger)) {
                final char[] chars = {currentChar, (char) newChar};
                bigramsFound.compute(new String(chars),
                        (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
            }
        }
        currentChar = (char) newChar;
        currentFinger = sfbCandidates.get(currentChar);
    }

    public double result(Long totalLetters) {
        final Long occurences = bigramsFound.values().stream().reduce(Long::sum).orElse(0L);
        return (double) occurences / totalLetters * 100;
    }

    public List<NGramFreq> resultAll() {
        return bigramsFound.entrySet()
                .stream()
                .map(e-> new NGramFreq(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingLong(NGramFreq::frequency).reversed())
                .toList();
    }
}
