package com.judalabs.keyboardplayground.keyboard;

public enum Finger {

    LEFT_PINKY(0),
    LEFT_RING(1),
    LEFT_MIDDLE(2),
    LEFT_INDEX(3),
    LEFT_THUMB(4),
    RIGHT_THUMB(6),
    RIGHT_INDEX(5),
    RIGHT_MIDDLE(7),
    RIGHT_RING(8),
    RIGHT_PINKY(9),
        ;

    public final int order;


    Finger(int order) {
        this.order = order;
    }
}
