package com.judalabs.keyboardplayground.keyboard;

public enum Finger {

    LEFT_PINKY(0, Side.LEFT),
    LEFT_RING(1, Side.LEFT),
    LEFT_MIDDLE(2, Side.LEFT),
    LEFT_INDEX(3, Side.LEFT),
    LEFT_THUMB(4, Side.LEFT),
    RIGHT_THUMB(6, Side.RIGHT),
    RIGHT_INDEX(5, Side.RIGHT),
    RIGHT_MIDDLE(7, Side.RIGHT),
    RIGHT_RING(8, Side.RIGHT),
    RIGHT_PINKY(9, Side.RIGHT),
        ;

    public final int order;
    private final Side side;


    Finger(int order, Side side) {
        this.order = order;
        this.side = side;
    }
}
