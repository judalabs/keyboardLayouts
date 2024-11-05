package com.judalabs.keyboardplayground.features.metrics;

@FunctionalInterface
public interface CollectorListener {

    double result(Long totalLetters);

    default String resultFormat(Long totalLetters) {
        return String.format("%s: %.2f%%", this.getClass().getSimpleName(), result(totalLetters));
    }
}
