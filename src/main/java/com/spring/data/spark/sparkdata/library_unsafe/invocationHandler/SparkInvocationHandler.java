package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler;

import com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.DataExtractor;
import com.spring.data.spark.sparkdata.library_unsafe.finalizers.Finalizer;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


// Now this class is set by factory

@Builder
public class SparkInvocationHandler implements InvocationHandler {
    private Class<?> model;
    private String pathToData;
    private DataExtractor dataExtractor;
    private Map<Method, List<SparkTransformation>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Dataset<Row> dataset = dataExtractor.load(pathToData, context);
        List<SparkTransformation> sparkTransformations = transformationChain.get(method);
        for (SparkTransformation sparkTransformation : sparkTransformations) {
            dataset = sparkTransformation.transform(dataset);
        }
        Finalizer finalizer = finalizerMap.get(method);
        return finalizer.doAction(dataset, model);
    }
}
