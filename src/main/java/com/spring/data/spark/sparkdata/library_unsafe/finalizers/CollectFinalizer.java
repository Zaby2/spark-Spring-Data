package com.spring.data.spark.sparkdata.library_unsafe.finalizers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component("collect")
public class CollectFinalizer implements Finalizer {
    @Override
    public Object doAction(Dataset<Row> dataset, Class<?> model) {
        Encoder<?> encoder = Encoders.bean(model);// wtf
        return dataset.as(encoder).collectAsList(); // now our dataset is mapped to model class
    }
}
