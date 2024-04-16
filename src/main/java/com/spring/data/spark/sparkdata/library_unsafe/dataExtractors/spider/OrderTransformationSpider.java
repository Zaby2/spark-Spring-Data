package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider;

import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SortTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolverImpl;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

@Component("orderBy")
public class OrderTransformationSpider implements TransformationSpider {
    WordResolver wordResolver = new WordResolverImpl();
    @Override
    public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
      //  wordResolver.resolveJavaMethodWords()
        return new Tuple2<>(new SortTransformation(), null);
    }
}
