package com.judalabs.keyboardplayground.shared.layout;

import com.judalabs.keyboardplayground.shared.keyboard.Finger;
import com.judalabs.keyboardplayground.shared.keyboard.KeyCode;

public record LayoutKey(
        int xPos,
        int yPos,
        int rotation,
        KeyCode keyCode,
        Finger finger,
        double units) {

    public static LayoutKey letter(KeyCode keyCode, Finger finger, int xPos, int yPos) {
        return new LayoutKey(xPos, yPos, 0, keyCode, finger, 1);
    }
}
