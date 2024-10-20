package com.judalabs.keyboardplayground.metrics;

public interface ProducesMetrics {

    default String resultFormat() {
        return String.format("%s: %.4f", this.getClass(), result());
    }

    double result(Long totalLetters);
}
