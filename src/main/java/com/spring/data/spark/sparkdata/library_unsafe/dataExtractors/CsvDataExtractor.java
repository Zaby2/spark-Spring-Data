package com.spring.data.spark.sparkdata.library_unsafe.dataExtractors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component("csv")
public class CsvDataExtractor implements DataExtractor {

    @Override
    public Dataset<Row> load(String pathToData, ConfigurableApplicationContext context) {
        return context.getBean(SparkSession.class)
                .read()
                .option("header", true)
                .option("inferSchema", true)
                .csv(pathToData);
    }
}
