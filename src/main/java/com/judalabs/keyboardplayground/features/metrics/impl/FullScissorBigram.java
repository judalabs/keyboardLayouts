package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import com.judalabs.keyboardplayground.shared.layout.CharData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Slf4j
@Component
@RequestScope
@RequiredArgsConstructor
class FullScissorBigram implements CollectorListener, InputLetterListener, WordReadListener {

    private final NgramCounter ngramCounter = new NgramCounter();
    private final KeyboardLayoutSupplier keyByChar;
    private CharData curr = null;

    public void compute(char newChar) {
        final CharData newData = new CharData(newChar, keyByChar.get(newChar));
        if (curr != null && isScissor(newData)) {
            ngramCounter.add(curr.getCharacter(), newChar);
        }
        curr = newData;
    }

    private boolean isScissor(CharData newData) {
        final boolean itsTwoRowTravel = curr.isSameFingerButDiffChar(newData) &&
                Math.abs(curr.getRow() - newData.getRow()) == 2;

        return itsTwoRowTravel && FingerHeightPref.isThisKeyInHigherPosThanItShould(curr.getFinger(), newData.getFinger());
    }

    public double result(Long totalLetters) {
        return ngramCounter.result(totalLetters);
    }

    public List<NGramFreq> getResults() {
        return ngramCounter.getResults();
    }

    @Override
    public void finished() {
        curr = null;
    }
}
