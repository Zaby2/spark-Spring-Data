package com.spring.data.spark.sparkdata.library.dataExtractors.spider;


import com.spring.data.spark.sparkdata.library.invocationHandler.sparkTransformations.SparkTransformation;
import org.apache.spark.sql.catalyst.expressions.Sec;

import java.util.List;
import java.util.Set;

// This class will get all transformations from the method, and work with them.
public interface TransformationSpider {
    SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNames);

}
