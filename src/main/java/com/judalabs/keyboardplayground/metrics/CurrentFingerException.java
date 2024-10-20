package com.judalabs.keyboardplayground.metrics;

public class CurrentFingerException extends RuntimeException {

    public CurrentFingerException(char newChar) {
        super(String.valueOf(newChar));
    }
}
