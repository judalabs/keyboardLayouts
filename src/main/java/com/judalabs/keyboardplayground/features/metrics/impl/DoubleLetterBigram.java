package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import com.judalabs.keyboardplayground.shared.layout.CharData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
class DoubleLetterBigram implements CollectorListener, InputLetterListener, WordReadListener {

    private final Map<String, Long> bigramsFound = new HashMap<>();
    private final KeyboardLayoutSupplier keyByChar;
    private CharData current = null;

    public void compute(char newChar) {
        if (current != null && Objects.equals(current.getCharacter(), newChar)) {
            final char[] chars = {current.getCharacter(), newChar};
            bigramsFound.compute(new String(chars),
                    (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
        }

        current = new CharData(newChar, keyByChar.get(newChar));
    }

    public double result(Long totalLetters) {
        final Long occurences = bigramsFound.values().stream().reduce(Long::sum).orElse(0L);
        return (double) occurences / totalLetters * 100;
    }

    public String getResults() {
        return bigramsFound.entrySet()
                .stream()
                .map(e -> new NGramFreq(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingLong(NGramFreq::frequency).reversed())
                .toList()
                .toString();
    }

    @Override
    public void finished() {
        current = null;
    }
}
