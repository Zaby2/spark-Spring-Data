package com.spring.data.spark.sparkdata.library.finalizers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

public class CollectFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        Encoder<?> encoder = Encoders.bean(model);// wtf
        return dataset.as(encoder).collectAsList(); // now our dataset is mapped to model class
    }
}
