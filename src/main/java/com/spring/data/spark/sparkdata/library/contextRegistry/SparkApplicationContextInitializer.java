package com.spring.data.spark.sparkdata.library.contextRegistry;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class SparkApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) { // now this context is almost empty
        registerSparkBean(applicationContext);
        
    }

    private void registerSparkBean(ConfigurableApplicationContext applicationContext) {
        SparkSession.builder().appName("null").master("local[*]").getOrCreate(); // need to get this params from env
    }
}
