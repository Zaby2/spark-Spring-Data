package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations;

import com.spring.data.spark.sparkdata.library_unsafe.spark_collection.OrderedSparkCollection;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.stream.Collectors;

public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedSparkCollection<Object> args) {
       return  dataset.orderBy(columnNames.get(0), columnNames.stream()
               .skip(1)
               .toArray(String[]::new));
    }
}
