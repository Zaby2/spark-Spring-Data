package com.spring.data.spark.sparkdata.library.wordsresolver;

import java.util.ArrayList;
import java.util.List;


// maybe here we don't need to parse a single word, but how to parse word like findBy?
public class WordResolverImpl implements WordResolver {
    @Override
    public List<String> resolveJavaMethodWords(String methodName) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        for (int i = 0; i < methodName.length(); i++) {
            char ch = methodName.charAt(i);
            if (Character.isUpperCase(ch)) {
                if (currentWord.length() > 0) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
            }
            currentWord.append(ch);
        }
        if (currentWord.length() > 0) {
            words.add(currentWord.toString());
        }
        return words;
    }
}
