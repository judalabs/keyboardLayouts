package com.judalabs.keyboardplayground.keyboard;

import java.util.List;

import static com.judalabs.keyboardplayground.keyboard.Finger.*;
import static com.judalabs.keyboardplayground.keyboard.KeyCode.*;

public class DefaultLayout {

    public static KeyboardLayout build() {
        return KeyboardLayout.builder()
                .hasThumbKeys(false)
                .staggered(Staggered.ROW_STAGGERED)
                .keyCodes(layoutKeys())
                .build();
    }

    private static List<LayoutKey> layoutKeys() {
        return List.of(
                LayoutKey.letter(KC_Q, LEFT_PINKY, 0, 0),
                LayoutKey.letter(KC_W, LEFT_RING, 0, 1),
                LayoutKey.letter(KC_E, LEFT_MIDDLE, 0, 2),
                LayoutKey.letter(KC_R, LEFT_INDEX, 0, 3),
                LayoutKey.letter(KC_T, LEFT_INDEX, 0, 4),
                LayoutKey.letter(KC_Y, RIGHT_INDEX, 0, 5),
                LayoutKey.letter(KC_U, RIGHT_INDEX, 0, 6),
                LayoutKey.letter(KC_I, RIGHT_MIDDLE, 0, 7),
                LayoutKey.letter(KC_O, RIGHT_RING, 0, 8),
                LayoutKey.letter(KC_P, RIGHT_PINKY, 0, 9),
                LayoutKey.letter(KC_A, LEFT_PINKY, 1, 0),
                LayoutKey.letter(KC_S, LEFT_RING, 1, 1),
                LayoutKey.letter(KC_D, LEFT_MIDDLE, 1, 2),
                LayoutKey.letter(KC_F, LEFT_INDEX, 1, 3),
                LayoutKey.letter(KC_G, LEFT_INDEX, 1, 4),
                LayoutKey.letter(KC_H, RIGHT_INDEX, 1, 5),
                LayoutKey.letter(KC_J, RIGHT_INDEX, 1, 6),
                LayoutKey.letter(KC_K, RIGHT_MIDDLE, 1, 7),
                LayoutKey.letter(KC_L, RIGHT_RING, 1, 8),
                LayoutKey.letter(KC_Z, LEFT_PINKY, 2, 9),
                LayoutKey.letter(KC_X, LEFT_RING, 3, 0),
                LayoutKey.letter(KC_C, LEFT_MIDDLE, 3, 1),
                LayoutKey.letter(KC_V, LEFT_INDEX, 3, 2),
                LayoutKey.letter(KC_B, LEFT_INDEX, 3, 3),
                LayoutKey.letter(KC_N, RIGHT_INDEX, 3, 4),
                LayoutKey.letter(KC_M, RIGHT_INDEX, 3, 5)
        );
    }
}
