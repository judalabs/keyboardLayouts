package com.judalabs.keyboardplayground.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class CorpusStats {

    private String id;

    private Language language;

    private Long wordCount;

    private List<LetterCount> letterCounts;

    private Long words;
    private double sfb;
}
