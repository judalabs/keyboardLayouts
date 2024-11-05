package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.shared.layout.LayoutKey;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequestScope
public class KeyboardLayoutSupplier {

    private Map<Character, LayoutKey> keyByChar;

    public void init(List<LayoutKey> keycodes) {
        keyByChar = keycodes.stream()
                .collect(Collectors.toMap(lk -> lk.keyCode().getNormalChar(), Function.identity()));
    }

    public LayoutKey get(Character key) {
        return keyByChar.get(key);
    }
}
