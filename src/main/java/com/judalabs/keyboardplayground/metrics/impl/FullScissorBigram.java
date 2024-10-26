package com.judalabs.keyboardplayground.metrics.impl;

import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import com.judalabs.keyboardplayground.metrics.CollectorListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FullScissorBigram implements CollectorListener {

    private final Map<String, Long> bigramsFound = new HashMap<>();
    private final Map<Character, LayoutKey> keyByChar;
    private CharData curr = null;

    public FullScissorBigram(Map<Character, LayoutKey> keyByChar) {
        this.keyByChar = keyByChar;
    }

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
        curr = null;
    }
}
