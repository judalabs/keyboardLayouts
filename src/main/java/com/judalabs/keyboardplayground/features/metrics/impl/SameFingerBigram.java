package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import com.judalabs.keyboardplayground.shared.keyboard.Finger;
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
class SameFingerBigram implements CollectorListener, WordReadListener, InputLetterListener {

    private final NgramCounter ngramCounter = new NgramCounter();
    private final KeyboardLayoutSupplier keyByChar;
    private CharData current;


    public void compute(char newChar) {
        if (current != null) {
            final Finger newFinger = keyByChar.get(newChar).finger();
            if (Objects.equals(current.getFinger(), newFinger) && !Objects.equals(current.getCharacter(), newChar)) {
                ngramCounter.add(current.getCharacter(), newChar);
            }
        }
        current = new CharData(newChar, keyByChar.get(newChar));
    }

    public double result(Long totalLetters) {
        return ngramCounter.result(totalLetters);
    }

    public List<NGramFreq> getResults() {
        return ngramCounter.getResults();
    }

    @Override
    public void finished() {
        current = null;
    }
}
