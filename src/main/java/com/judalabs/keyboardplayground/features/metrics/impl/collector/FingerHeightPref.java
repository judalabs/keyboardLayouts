package com.judalabs.keyboardplayground.features.metrics.impl.collector;

import com.judalabs.keyboardplayground.shared.keyboard.Finger;

enum FingerHeightPref {

    INDEX(1, Finger.LEFT_INDEX, Finger.RIGHT_INDEX),
    MIDDLE(4, Finger.LEFT_MIDDLE, Finger.RIGHT_MIDDLE),
    RING(3, Finger.LEFT_RING, Finger.RIGHT_RING),
    PINKY(2, Finger.LEFT_PINKY, Finger.RIGHT_PINKY);

    private final int height;
    private final Finger left;
    private final Finger right;

    FingerHeightPref(int height, Finger left, Finger right) {
        this.height = height;
        this.left = left;
        this.right = right;

    }

    public static boolean isThisKeyInHigherPosThanItShould(Finger current, Finger newFinger) {
        int heightCurr = 0;
        int heightNew = 0;
        for (FingerHeightPref heightPref : values()) {
            heightCurr = Math.max(heightCurr, getFingerHeight(current, heightPref));
            heightNew = Math.max(heightNew, getFingerHeight(newFinger, heightPref));
        }
        return heightNew < heightCurr;
    }

    private static int getFingerHeight(Finger finger, FingerHeightPref heightPref) {
        return heightPref.left == finger || heightPref.right == finger ? heightPref.height : 0;
    }
}
