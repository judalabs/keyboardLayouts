package com.judalabs.keyboardplayground.metrics.impl;

import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import com.judalabs.keyboardplayground.metrics.CollectorListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DoubleLetterBigram implements CollectorListener {

    private final Map<String, Long> bigramsFound = new HashMap<>();
    private final Map<Character, LayoutKey> fingerByChar;
    private CharData current;

    public DoubleLetterBigram(Map<Character, LayoutKey> fingerByChar) {
        this.fingerByChar = fingerByChar;
    }

    public void compute(char newChar) {
        if (current != null && Objects.equals(current.getCharacter(), newChar)) {
                final char[] chars = {current.getCharacter(), newChar};
                bigramsFound.compute(new String(chars),
                        (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
            }

        current = new CharData(newChar, fingerByChar.get(newChar));
    }

    public double result(Long totalLetters) {
        final Long occurences = bigramsFound.values().stream().reduce(Long::sum).orElse(0L);
        return (double) occurences / totalLetters * 100;
    }

    public String getResults() {
        return bigramsFound.entrySet()
                .stream()
                .map(e-> new NGramFreq(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingLong(NGramFreq::frequency).reversed())
                .toList()
                .toString();
    }

    @Override
    public void finished() {
        current = null;
    }
}
