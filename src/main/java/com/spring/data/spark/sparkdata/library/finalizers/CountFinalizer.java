package com.spring.data.spark.sparkdata.library.finalizers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class CountFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        return dataset.count();
    }
}
