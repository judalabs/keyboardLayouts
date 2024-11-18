package com.judalabs.keyboardplayground.features.metrics.impl.collector;

import com.judalabs.keyboardplayground.features.metrics.CollectorListener;
import com.judalabs.keyboardplayground.features.metrics.InputLetterListener;
import com.judalabs.keyboardplayground.features.metrics.WordReadListener;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EventCollector {

    private final List<InputLetterListener> inputLetterListeners;
    private final List<WordReadListener> wordReadListeners;
    private final List<CollectorListener> collectorListeners;

    public void notifyNewWord() {
        wordReadListeners.forEach(WordReadListener::finished);
    }

    public void compute(int elem) {
        inputLetterListeners.forEach(newLetterListener -> newLetterListener.compute((char) elem));
    }

    public String getResultsFromListeners(Long totalLetters) {
        return collectorListeners.stream()
                .map(newLetterListener -> newLetterListener.resultFormat(totalLetters))
                .collect(Collectors.joining("\n\t", "\t", ""));
    }
}
