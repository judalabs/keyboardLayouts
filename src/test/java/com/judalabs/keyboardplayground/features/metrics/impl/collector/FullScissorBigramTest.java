package com.judalabs.keyboardplayground.features.metrics.impl.collector;

import com.judalabs.keyboardplayground.features.metrics.impl.KeyboardLayoutSupplier;
import com.judalabs.keyboardplayground.features.metrics.impl.NGramFreq;
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
class FullScissorBigramTest {

    @InjectMocks
    private FullScissorBigram fullScissorBigram;

    @Spy
    private KeyboardLayoutSupplier keyByChar = new KeyboardLayoutSupplier();

    @BeforeEach
    void before() {
        keyByChar.init(DefaultLayout.build().getKeyCodes());
    }

    @Test
    void shouldReturnNoBigramsWhenHas1LetterProcessed(){
        fullScissorBigram.compute('A');
        assertEquals(0, fullScissorBigram.getResults().size());
    }
    @Test
    @DisplayName("Should not count half scissors")
    void shouldntCountHalfScissors(){
        fullScissorBigram.compute('c');
        fullScissorBigram.compute('v');
        fullScissorBigram.compute('f');
        final List<NGramFreq> results = fullScissorBigram.getResults();
        assertEquals(0, results.size());
    }

    @Test
    @DisplayName("Should return a bigram when same finger press two diff digits in sequence")
    void shouldCountWhenFingerCrossBetweenUnbalancedLengths(){
        fullScissorBigram.compute('c');
        fullScissorBigram.compute('v');
        fullScissorBigram.compute('r');
        final List<NGramFreq> results = fullScissorBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(1, results.getFirst().frequency());
        assertEquals("vr", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should return sum of frequency")
    void shouldReturnsSumOfrequency(){
        fullScissorBigram.compute('c');
        fullScissorBigram.compute('v');
        fullScissorBigram.compute('r');

        fullScissorBigram.compute('i'); // letter to reset the bigram

        fullScissorBigram.compute('c');
        fullScissorBigram.compute('v');
        fullScissorBigram.compute('r');

        final List<NGramFreq> results = fullScissorBigram.getResults();
        assertEquals(1, results.size());
        assertEquals(2, results.getFirst().frequency());
        assertEquals("vr", results.getFirst().substring());
    }

    @Test
    @DisplayName("Should not return a bigram when same finger press two diff digits not in sequence")
    void shouldNotReturnABigramWhenSameFingerPressTwoDiffDigitsNotInSequence(){
        fullScissorBigram.compute('a');
        fullScissorBigram.compute('t');
        fullScissorBigram.compute('a');
        assertEquals(0, fullScissorBigram.getResults().size());
    }
}