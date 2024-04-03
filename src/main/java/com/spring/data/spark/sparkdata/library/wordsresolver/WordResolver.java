package com.spring.data.spark.sparkdata.library.wordsresolver;

import java.util.List;
import java.util.Set;

public interface WordResolver {
    public List<String> resolveJavaMethodWords(String methodName);

    public String findAndRemoveMatchingMethodNamesIfExists(Set<String> options,List<String> words);
}
