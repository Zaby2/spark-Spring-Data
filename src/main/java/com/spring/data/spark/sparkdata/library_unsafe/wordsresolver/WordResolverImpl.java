package com.spring.data.spark.sparkdata.library_unsafe.wordsresolver;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
    // need to add test for this, but mne vlom
    @Override
    public String findAndRemoveMatchingMethodNamesIfExists(Set<String> options, List<String> words) {
        StringBuilder match = new StringBuilder(words.remove(0));
        List<String> remainOptions = options.stream().filter(option -> option.toLowerCase().startsWith(match.toString().toLowerCase())).toList();
        if(remainOptions.isEmpty()) {
            return "";
        }
        while (remainOptions.size() > 1) {
            match.append(words.remove(0));
            remainOptions.removeIf(option -> !option.toLowerCase().startsWith(match.toString().toLowerCase()));
        }
        while(!remainOptions.get(0).equalsIgnoreCase(match.toString())) {
            match.append(words.remove(0));
        }
        return Introspector.decapitalize(match.toString());
    }
}
