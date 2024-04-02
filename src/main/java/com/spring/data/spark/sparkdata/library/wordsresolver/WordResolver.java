package com.spring.data.spark.sparkdata.library.wordsresolver;

import java.util.List;

public interface WordResolver {
    public List<String> resolveJavaMethodWords(String methodName);
}
