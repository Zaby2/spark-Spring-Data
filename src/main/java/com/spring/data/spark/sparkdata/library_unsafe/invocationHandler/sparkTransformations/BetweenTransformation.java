package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations;

import com.spring.data.spark.sparkdata.library_unsafe.spark_collection.OrderedSparkCollection;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

// we need to remember about stateless, so we cant inject list of columnNames directly here

@Component("between")
public class BetweenTransformation implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedSparkCollection<Object> args) {
        dataset.filter(functions.col(columnNames.get(0)).between(args.takeAndRemove(), args.takeAndRemove()));
        return null;
    }
}
