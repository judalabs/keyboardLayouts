package com.judalabs.keyboardplayground.shared.layout;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class KeyboardLayout {

    private String id;

    private Staggered staggered;

    private boolean hasThumbKeys;

    private List<LayoutKey> keyCodes;
}
