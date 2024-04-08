package com.spring.data.spark.sparkdata.library.invocationHandler;

import com.spring.data.spark.sparkdata.library.dataExtractors.DataExtractor;
import com.spring.data.spark.sparkdata.library.finalizers.Finalizer;
import com.spring.data.spark.sparkdata.library.invocationHandler.sparkTransformations.SparkTransformation;
import lombok.Builder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


// So this class need to be set by factory

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
        Object retVal = finalizer.doAction(dataset, model);
        return retVal;
    }
}
