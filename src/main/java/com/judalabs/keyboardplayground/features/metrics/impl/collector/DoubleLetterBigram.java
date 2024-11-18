package com.judalabs.keyboardplayground.features.metrics.impl.collector;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import com.judalabs.keyboardplayground.features.metrics.impl.KeyboardLayoutSupplier;
import com.judalabs.keyboardplayground.features.metrics.impl.NGramFreq;
import com.judalabs.keyboardplayground.features.metrics.impl.NgramCounter;
import com.judalabs.keyboardplayground.shared.layout.CharData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
class DoubleLetterBigram implements CollectorListener, InputLetterListener, WordReadListener {

    private final NgramCounter ngramCounter = new NgramCounter();
    private final KeyboardLayoutSupplier keyByChar;
    private CharData current = null;

    public void compute(char newChar) {
        if (current != null && Objects.equals(current.getCharacter(), newChar)) {
            ngramCounter.add(current.getCharacter(), newChar);
        }

        current = new CharData(newChar, keyByChar.get(newChar));
    }

    @Override
    public void finished() {
        current = null;
    }

    @Override
    public double result(Long totalLetters) {
        return ngramCounter.result(totalLetters);
    }

    public List<NGramFreq> getResults() {
        return ngramCounter.getResults();

    }
}
