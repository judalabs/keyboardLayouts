package com.judalabs.keyboardplayground.metrics.impl;

import com.judalabs.keyboardplayground.keyboard.Finger;
import com.judalabs.keyboardplayground.keyboard.LayoutKey;
import lombok.Getter;

import java.util.Objects;

@Getter
public class CharData {

    private final Character character;
    private final LayoutKey layoutKey;

    public CharData(int character, LayoutKey keyCode) {
        this.character = (char) character;
        this.layoutKey = keyCode;
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

    public Finger getFinger() {
        return layoutKey.finger();
    }
}
