package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider;


import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

// This class will get all transformations from the method, and work with them.
public interface TransformationSpider {
    Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames);

}
