package com.judalabs.keyboardplayground.features.metrics.impl;

import com.judalabs.keyboardplayground.features.metrics.impl.collector.DoubleLetterBigram;
import com.judalabs.keyboardplayground.shared.layout.DefaultLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DoubleLetterBigramTest {

    @InjectMocks
    private DoubleLetterBigram doubleLetterBigram;

    @Spy
    private KeyboardLayoutSupplier keyByChar = new KeyboardLayoutSupplier();

    @BeforeEach
    void before() {
        keyByChar.init(DefaultLayout.build().getKeyCodes());
    }

    @Test
    void shouldReturnNoBigramsWhenHas1LetterProcessed(){
        doubleLetterBigram.compute('A');
        assertEquals(0, doubleLetterBigram.getResults().size());
    }

    @Test
    @DisplayName("Should return a bigram when same finger press two diff digits in sequence")
    void shouldReturnABigramWhenSameFingerPressTwoDiffDigitsInSequence(){
        doubleLetterBigram.compute('a');
        doubleLetterBigram.compute('a');
        final List<NGramFreq> results = doubleLetterBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(1, results.getFirst().frequency());
        assertEquals("aa", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should return sum of frequency")
    void shouldReturnsSumOfrequency(){
        doubleLetterBigram.compute('a');
        doubleLetterBigram.compute('a');
        doubleLetterBigram.compute('k');// letter to reset the bigram
        doubleLetterBigram.compute('a');
        doubleLetterBigram.compute('a');

        final List<NGramFreq> results = doubleLetterBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(2, results.getFirst().frequency());
        assertEquals("aa", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should not return a bigram when same finger press two diff digits not in sequence")
    void shouldNotReturnABigramWhenSameFingerPressTwoDiffDigitsNotInSequence(){
        doubleLetterBigram.compute('a');
        doubleLetterBigram.compute('t');
        doubleLetterBigram.compute('a');
        assertEquals(0, doubleLetterBigram.getResults().size());
    }
}