package com.spring.data.spark.sparkdata.library.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

// this class is used for auto - complete idea training in application properties
@ConfigurationProperties(prefix = "spark")
public class SparkPropsHandler {
    private String appName;
    private String packagesToScan;
}
