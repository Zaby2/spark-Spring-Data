package com.spring.data.spark.sparkdata.library_unsafe.props;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

// this class is used for auto - complete idea training in application properties
@ConfigurationProperties(prefix = "spark")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SparkPropsHandler {
    private String appName;
    private String packagesToScan;
}
