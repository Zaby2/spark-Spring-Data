package com.spring.data.spark.sparkdata.library.finalizers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface Finalizer {
    Object doAction(Dataset<Row> dataset, Class<?> model);
}
