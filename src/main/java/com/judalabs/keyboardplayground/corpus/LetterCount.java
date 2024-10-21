package com.judalabs.keyboardplayground.corpus;

import java.util.Comparator;
import java.util.StringJoiner;

public record LetterCount(char letter, Long count, double frequency) implements Comparable<LetterCount> {

    @Override
    public int compareTo(LetterCount that) {
        return Comparator
                .comparingDouble(LetterCount::frequency)
                .reversed().compare(this, that);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "\n{", "}")
                .add("letter = " + letter)
                .add("count = " + count)
                .add(String.format("frequency = %.2f%%", frequency))
                .toString();
    }
}
