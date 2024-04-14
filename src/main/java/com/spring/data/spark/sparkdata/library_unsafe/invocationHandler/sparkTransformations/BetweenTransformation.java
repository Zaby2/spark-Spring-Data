package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

@Component("between")
public class BetweenTransformation implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset) {
        dataset.filter(functions.col(fieldName).between(low, high)); // how to get this fields
        return null;
    }
}
