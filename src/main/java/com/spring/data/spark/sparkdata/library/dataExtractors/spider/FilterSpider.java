package com.spring.data.spark.sparkdata.library.dataExtractors.spider;

import com.spring.data.spark.sparkdata.library.invocationHandler.sparkTransformations.FilterTransformation;
import com.spring.data.spark.sparkdata.library.invocationHandler.sparkTransformations.SparkTransformation;
import com.spring.data.spark.sparkdata.library.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library.wordsresolver.WordResolverImpl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FilterSpider implements TransformationSpider {
    private Map<String, FilterTransformation> filterTransformationMap;
    WordResolver wordResolver = new WordResolverImpl();

    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {
        String fieldName = wordResolver.findAndRemoveMatchingMethodNamesIfExists(fieldNames, methodWords);
        String filterName = wordResolver.findAndRemoveMatchingMethodNamesIfExists(filterTransformationMap.keySet(), methodWords);
        return filterTransformationMap.get(filterName);
    }
}
