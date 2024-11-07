package com.judalabs.keyboardplayground.features.metrics.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NgramCounter {

    private final Map<String, Long> bigramsFound = new HashMap<>();

    public List<NGramFreq> getResults() {
        return bigramsFound.entrySet()
                .stream()
                .map(e -> new NGramFreq(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingLong(NGramFreq::frequency).reversed())
                .toList();
    }

    public double result(Long totalLetters) {
        final Long occurences = bigramsFound.values().stream().reduce(Long::sum).orElse(0L);
        return (double) occurences / totalLetters * 100;
    }

    public void add(char... chars) {
        bigramsFound.compute(new String(chars),
                (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
    }
}
