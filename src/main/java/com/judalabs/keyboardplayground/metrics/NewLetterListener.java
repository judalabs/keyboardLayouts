package com.judalabs.keyboardplayground.metrics;

public interface NewLetterListener {

    void compute(int character);

    double result(Long totalLetters);

    default String resultFormat(Long totalLetters) {
        return String.format("%s: %.4f", this.getClass().getSimpleName(), result(totalLetters));
    }
}
