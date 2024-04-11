package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider;

import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.FilterTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolverImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("findBy")
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
