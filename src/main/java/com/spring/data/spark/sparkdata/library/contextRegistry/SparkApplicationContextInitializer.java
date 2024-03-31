package com.spring.data.spark.sparkdata.library.contextRegistry;

import com.spring.data.spark.sparkdata.library.repository.SparkRepository;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.beans.Introspector;
import java.lang.reflect.Proxy;
import java.util.Set;

public class SparkApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) { // now this context is almost empty
        registerSparkBean(applicationContext);
        String scannerPath = applicationContext.getEnvironment().getProperty("spark.packages-to-scan");
        Reflections scanner = new Reflections(scannerPath);
        Set<Class<? extends SparkRepository>> sparkRepoImpls = scanner.getSubTypesOf(SparkRepository.class);

        sparkRepoImpls.forEach(sparkRepoImpl -> {
            Object newProxyInstance = Proxy.newProxyInstance(sparkRepoImpl.getClassLoader(), new Class[]{SparkRepository.class}, );
            applicationContext.getBeanFactory().registerSingleton(Introspector.decapitalize(sparkRepoImpl.getSimpleName()), newProxyInstance); // need to create and set
        });

    }


    // Later need to add params that are required during spark configuration
    private void registerSparkBean(ConfigurableApplicationContext applicationContext) {
        String appName = applicationContext.getEnvironment().getProperty("spark.app-name");
        SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate(); // need to create beans that will be used based on profile
        JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
        applicationContext.getBeanFactory().registerSingleton("sparkContext", sparkContext);
        applicationContext.getBeanFactory().registerSingleton("sparkSession", sparkSession);
    }
}
