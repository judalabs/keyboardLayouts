package com.judalabs.keyboardplayground.features.metrics;

@FunctionalInterface
public interface InputLetterListener {

    void compute(char character);
}
