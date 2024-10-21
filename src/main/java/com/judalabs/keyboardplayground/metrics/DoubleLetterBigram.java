package com.judalabs.keyboardplayground.metrics;

import com.judalabs.keyboardplayground.keyboard.Finger;
import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DoubleLetterBigram implements CollectorListener {

    private final Map<Character, Finger> sfbCandidates;
    private final Map<String, Long> bigramsFound = new HashMap<>();
    private Character currentChar = null;
    private Finger currentFinger = null;

    public DoubleLetterBigram(List<LayoutKey> layoutKeys) {
        sfbCandidates = layoutKeys.stream()
                .collect(Collectors.toMap(lk -> lk.keyCode().getNormalChar(), LayoutKey::finger));
    }

    public void compute(int newChar) {
        if (currentChar != null) {
            if (Objects.equals(currentChar, (char) newChar)) {
                final char[] chars = {currentChar, (char) newChar};
                bigramsFound.compute(new String(chars),
                        (oldValue, newValue) -> newValue == null ? 1 : newValue + 1);
            } else if (currentFinger == null){
                log.error("currentFinger shoudn't be null");
                throw new CurrentFingerException((char) newChar);
            }
        }
        currentChar = (char) newChar;
        currentFinger = sfbCandidates.get(currentChar);
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
        currentChar = null;
        currentFinger = null;
    }
}
