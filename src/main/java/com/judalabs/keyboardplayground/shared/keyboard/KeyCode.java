package com.judalabs.keyboardplayground.shared.keyboard;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.judalabs.keyboardplayground.shared.keyboard.KeyCode.TypeKeyCode.CONTROL;
import static com.judalabs.keyboardplayground.shared.keyboard.KeyCode.TypeKeyCode.FN;
import static com.judalabs.keyboardplayground.shared.keyboard.KeyCode.TypeKeyCode.LETTER;
import static com.judalabs.keyboardplayground.shared.keyboard.KeyCode.TypeKeyCode.NUMBER;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.toMap;

public enum KeyCode {

    KC_Q(LETTER, 'q', 'Q'),
    KC_W(LETTER, 'w', 'W'),
    KC_E(LETTER, 'e', 'E'),
    KC_R(LETTER, 'r', 'R'),
    KC_T(LETTER, 't', 'T'),
    KC_Y(LETTER, 'y', 'Y'),
    KC_U(LETTER, 'u', 'U'),
    KC_I(LETTER, 'i', 'I'),
    KC_O(LETTER, 'o', 'O'),
    KC_P(LETTER, 'p', 'P'),
    KC_A(LETTER, 'a', 'A'),
    KC_S(LETTER, 's', 'S'),
    KC_D(LETTER, 'd', 'D'),
    KC_F(LETTER, 'f', 'F'),
    KC_G(LETTER, 'g', 'G'),
    KC_H(LETTER, 'h', 'H'),
    KC_J(LETTER, 'j', 'J'),
    KC_K(LETTER, 'k', 'K'),
    KC_L(LETTER, 'l', 'L'),
    KC_Z(LETTER, 'z', 'Z'),
    KC_X(LETTER, 'x', 'X'),
    KC_C(LETTER, 'c', 'C'),
    KC_V(LETTER, 'v', 'V'),
    KC_B(LETTER, 'b', 'B'),
    KC_N(LETTER, 'n', 'N'),
    KC_M(LETTER, 'm', 'M'),

    KC_0(NUMBER, '0', null),
    KC_1(NUMBER, '1', null),
    KC_2(NUMBER, '2', null),
    KC_3(NUMBER, '3', null),
    KC_4(NUMBER, '4', null),
    KC_5(NUMBER, '5', null),
    KC_6(NUMBER, '6', null),
    KC_7(NUMBER, '7', null),
    KC_8(NUMBER, '8', null),
    KC_9(NUMBER, '9', null),

    KC_F1(FN, null, null),
    KC_F2(FN, null, null),
    KC_F3(FN, null, null),
    KC_F4(FN, null, null),
    KC_F5(FN, null, null),
    KC_F6(FN, null, null),
    KC_F7(FN, null, null),
    KC_F8(FN, null, null),
    KC_F9(FN, null, null),
    KC_F10(FN, null, null),
    KC_F11(FN, null, null),
    KC_F12(FN, null, null),
    KC_F13(FN, null, null),
    KC_F14(FN, null, null),
    KC_F15(FN, null, null),
    KC_F16(FN, null, null),
    KC_F17(FN, null, null),
    KC_F18(FN, null, null),
    KC_F19(FN, null, null),
    KC_F20(FN, null, null),
    KC_F21(FN, null, null),
    KC_F22(FN, null, null),
    KC_F23(FN, null, null),
    KC_F24(FN, null, null),

    KC_ESCAPE(CONTROL, null, null),
    KC_SPACE(CONTROL, null, null),
    KC_COMMA(CONTROL, null, null),
    KC_DOT(CONTROL, null, null);

    KeyCode(TypeKeyCode type, Character normalChar, Character alterChar) {
        this.type = type;
        this.normalChar = normalChar;
        this.alterChar = alterChar;
    }

    static {
        charToKeyCodeMap = Arrays.stream(KeyCode.values())
                .collect(
                        Collectors.teeing(
                                filtering(e -> e.getNormalChar() != null, toMap(KeyCode::getNormalChar, Function.identity())),
                                filtering(e -> e.getAlterChar() != null, toMap(KeyCode::getAlterChar, Function.identity())),
                                (a, b) -> {
                                    b.forEach((k, v) -> a.merge(k, v, (prev, cur) -> prev));
                                    return a;
                                }
                        ));
    }

    public static KeyCode getKeyCode(@NonNull Character character) {
        return charToKeyCodeMap.get(character);
    }


    public enum TypeKeyCode {
        LETTER, NUMBER, FN, CONTROL;
    }

    private final TypeKeyCode type;
    private final Character normalChar;
    private final Character alterChar;
    private static final Map<Character, KeyCode> charToKeyCodeMap;

    public static List<KeyCode> letters() {
        return getKeyCodes(LETTER);
    }

    public static List<KeyCode> numbers() {
        return getKeyCodes(TypeKeyCode.NUMBER);
    }

    private static List<KeyCode> getKeyCodes(TypeKeyCode type) {
        return EnumSet.allOf(KeyCode.class)
                .stream().filter(e -> e.type == type)
                .toList();
    }

    public TypeKeyCode getType() {
        return type;
    }

    public Character getNormalChar() {
        return normalChar;
    }

    public Character getAlterChar() {
        return alterChar;
    }
}
