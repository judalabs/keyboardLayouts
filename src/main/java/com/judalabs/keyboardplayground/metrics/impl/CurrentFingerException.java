package com.judalabs.keyboardplayground.metrics.impl;

public class CurrentFingerException extends RuntimeException {

    public CurrentFingerException(char newChar) {
        super(String.valueOf(newChar));
    }
}
