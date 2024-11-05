package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.KeyboardLayoutSupplier;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import com.judalabs.keyboardplayground.shared.layout.CharData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
class FullScissorBigram implements CollectorListener, InputLetterListener, WordReadListener {

    private final Map<String, Long> bigramsFound = new HashMap<>();
    private final KeyboardLayoutSupplier keyByChar;
    private CharData curr = null;

    public void compute(char newChar) {
        final CharData newData = new CharData(newChar, keyByChar.get(newChar));
        if (curr != null && isScissor(newData)) {
            final char[] chars = {curr.getCharacter(), newChar};
            bigramsFound.compute(new String(chars),
                    (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
        }
        curr = newData;
    }

    private boolean isScissor(CharData newData) {
        final boolean itsTwoRowTravel = curr.isSameFingerButDiffChar(newData) &&
                Math.abs(curr.getRow() - newData.getRow()) == 2;

        return itsTwoRowTravel && FingerHeightPref.isThisKeyInHigherPosThanItShould(curr.getFinger(), newData.getFinger());
    }

    public double result(Long totalLetters) {
        final Long occurences = bigramsFound.values().stream().reduce(Long::sum).orElse(0L);
        return (double) occurences / totalLetters * 100;
    }

    public List<NGramFreq> getResults() {
        return bigramsFound.entrySet()
                .stream()
                .map(e -> new NGramFreq(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingLong(NGramFreq::frequency).reversed())
                .toList();
    }

    @Override
    public void finished() {
        curr = null;
    }
}
