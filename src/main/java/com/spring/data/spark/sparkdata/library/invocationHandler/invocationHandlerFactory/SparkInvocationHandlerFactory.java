package com.spring.data.spark.sparkdata.library.invocationHandler.invocationHandlerFactory;

import com.spring.data.spark.sparkdata.library.annotation.Source;
import com.spring.data.spark.sparkdata.library.annotation.Transient;
import com.spring.data.spark.sparkdata.library.dataExtractors.DataExtractor;
import com.spring.data.spark.sparkdata.library.dataExtractors.dataresolver.DataExtractorResolver;
import com.spring.data.spark.sparkdata.library.invocationHandler.SparkInvocationHandler;
import com.spring.data.spark.sparkdata.library.repository.SparkRepository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class SparkInvocationHandlerFactory {

    private DataExtractorResolver resolver;

    public SparkInvocationHandler createSparkInvocationHandler(Class<? extends SparkRepository> sparkRepositoryImpl) {
        Class<?> modelClass = getModelClass(sparkRepositoryImpl);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(sparkRepositoryImpl);
        DataExtractor dataExtractor = resolver.resolve(pathToData);

        return SparkInvocationHandler.builder()
                .model(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor).build(); // not complete

    }

    private Set<String> getFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType())) // This part avoid downloading collections as fields
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> sparkRepositoryImpl) {
        ParameterizedType parameterizedType = (ParameterizedType) sparkRepositoryImpl.getGenericInterfaces()[0];
        return  (Class<?>) parameterizedType.getActualTypeArguments()[0];

    }
}
