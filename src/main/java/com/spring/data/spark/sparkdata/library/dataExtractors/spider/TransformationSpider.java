package com.spring.data.spark.sparkdata.library.dataExtractors.spider;


import com.spring.data.spark.sparkdata.library.invocationHandler.SparkTransformation;

import java.util.List;

// This class will get all transformations from the method, and work with them.
public interface TransformationSpider {
    SparkTransformation getTransformation(List<String> methodWords);

}
