package com.judalabs.keyboardplayground.features.metrics;

import com.judalabs.keyboardplayground.features.metrics.impl.EventCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventCollectorTest {

    EventCollector eventCollector;
    @Mock
    InputLetterListener inputLetterListeners;
    @Mock
    WordReadListener wordReadListeners;
    @Mock
    CollectorListener collectorListeners;

    @BeforeEach
    void before() {
        eventCollector = new EventCollector(
                List.of(inputLetterListeners, inputLetterListeners),
                List.of(wordReadListeners, wordReadListeners),
                List.of(collectorListeners, collectorListeners));
    }

    @Test
    void shouldCallWordListeners() {

        eventCollector.notifyNewWord();

        verify(wordReadListeners, times(2)).finished();
    }

    @Test
    void shouldCallInputListeners() {

        eventCollector.compute(65);

        verify(inputLetterListeners, times(2)).compute('A');
    }

    @Test
    void shouldCallCollectorListeners() {

        eventCollector.getResultsFromListeners(3L);

        verify(collectorListeners, times(2)).resultFormat(3L);
    }
}