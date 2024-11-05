package com.judalabs.keyboardplayground.shared.layout;

import com.judalabs.keyboardplayground.shared.keyboard.Finger;
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

    public int getRow() {
        return layoutKey.xPos();
    }

    public boolean isSameFingerButDiffChar(CharData newData) {
        return this.getFinger().equals(newData.getFinger()) &&
                !this.character.equals(newData.character);
    }

}
