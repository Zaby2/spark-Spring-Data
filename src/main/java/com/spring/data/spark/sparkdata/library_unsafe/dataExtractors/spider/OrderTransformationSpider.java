package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider;

import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component("orderBy")
public class OrderTransformationSpider implements TransformationSpider {
    @Override
    public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames) {
        return null;
    }
}
