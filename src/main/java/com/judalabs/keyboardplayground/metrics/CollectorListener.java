package com.judalabs.keyboardplayground.metrics;

public interface CollectorListener {

    void compute(int character);

    double result(Long totalLetters);

    default String resultFormat(Long totalLetters) {
        return String.format("%s: %.2f%%", this.getClass().getSimpleName(), result(totalLetters));
    }

    void finished();
}
