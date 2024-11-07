package com.judalabs.keyboardplayground.shared.corpus;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.StringJoiner;

@Builder
@Getter
public class CorpusStats {

    private String id;

    private Language language;

    private List<LetterCount> letterCounts;
    
    private Long words;

    private String results;

    @Override
    public String toString() {
        return new StringJoiner(", ", CorpusStats.class.getSimpleName() + "[", "]")
                .add("language=" + language)
                .add("words=" + words)
                .add("letterCounts=" + letterCounts)
                .add("\nresults=\n" + results + "\n")
                .toString();
    }
}
