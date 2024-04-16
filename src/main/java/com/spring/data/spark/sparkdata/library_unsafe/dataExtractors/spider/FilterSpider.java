package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider;

import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.FilterTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolverImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("findBy")
@RequiredArgsConstructor
public class FilterSpider implements TransformationSpider {
    private final Map<String, FilterTransformation> filterTransformationMap;
    WordResolver wordResolver = new WordResolverImpl();

    @Override
    public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
        List<String> columnNames = List.of(wordResolver.findAndRemoveMatchingMethodNamesIfExists(fieldNames, methodWords));
        String filterName = wordResolver.findAndRemoveMatchingMethodNamesIfExists(filterTransformationMap.keySet(), methodWords);
        return new Tuple2<>(filterTransformationMap.get(filterName), columnNames);
    }
}
