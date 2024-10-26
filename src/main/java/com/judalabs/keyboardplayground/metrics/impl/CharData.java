package com.judalabs.keyboardplayground.metrics.impl;

import com.judalabs.keyboardplayground.keyboard.Finger;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CharData {

    private final Character character;
    private final Finger finger;

    public CharData(int character, Finger finger) {
        this.character = (char) character;
        this.finger = finger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharData charData = (CharData) o;
        return Objects.equals(character, charData.character);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(character);
    }
}
