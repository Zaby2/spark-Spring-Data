package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations;

import com.spring.data.spark.sparkdata.library_unsafe.spark_collection.OrderedSparkCollection;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

public interface SparkTransformation {
    Dataset<Row> transform(Dataset<Row> dataset, List<String> columNames, OrderedSparkCollection<Object> args);
}
