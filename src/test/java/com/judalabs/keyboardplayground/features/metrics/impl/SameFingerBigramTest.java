package com.judalabs.keyboardplayground.features.metrics.impl;

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
class SameFingerBigramTest {

    @InjectMocks
    private SameFingerBigram sameFingerBigram;

    @Spy
    private KeyboardLayoutSupplier keyByChar = new KeyboardLayoutSupplier();

    @BeforeEach
    void before() {
        keyByChar.init(DefaultLayout.build().getKeyCodes());
    }

    @Test
    void shouldReturnNoBigramsWhenHas1LetterProcessed(){
        sameFingerBigram.compute('A');
        assertEquals(0, sameFingerBigram.getResults().size());
    }

    @Test
    @DisplayName("Should return a bigram when same finger press two diff digits in sequence")
    void shouldReturnABigramWhenSameFingerPressTwoDiffDigitsInSequence(){
        sameFingerBigram.compute('a');
        sameFingerBigram.compute('z');
        final List<NGramFreq> results = sameFingerBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(1, results.getFirst().frequency());
        assertEquals("az", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should return sum of frequency")
    void shouldReturnsSumOfrequency(){
        sameFingerBigram.compute('a');
        sameFingerBigram.compute('z');
        sameFingerBigram.compute('k');// letter to reset the bigram
        sameFingerBigram.compute('a');
        sameFingerBigram.compute('z');

        final List<NGramFreq> results = sameFingerBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(2, results.getFirst().frequency());
        assertEquals("az", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should not return a bigram when same finger press two diff digits not in sequence")
    void shouldNotReturnABigramWhenSameFingerPressTwoDiffDigitsNotInSequence(){
        sameFingerBigram.compute('a');
        sameFingerBigram.compute('t');
        sameFingerBigram.compute('z');
        assertEquals(0, sameFingerBigram.getResults().size());
    }
}