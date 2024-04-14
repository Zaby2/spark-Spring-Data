package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset) {
        return null;
    }
}
