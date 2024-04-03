package com.spring.data.spark.sparkdata.library.invocationHandler.invocationHandlerFactory;

import com.spring.data.spark.sparkdata.library.annotation.Source;
import com.spring.data.spark.sparkdata.library.annotation.Transient;
import com.spring.data.spark.sparkdata.library.dataExtractors.DataExtractor;
import com.spring.data.spark.sparkdata.library.dataExtractors.dataresolver.DataExtractorResolver;
import com.spring.data.spark.sparkdata.library.dataExtractors.spider.TransformationSpider;
import com.spring.data.spark.sparkdata.library.invocationHandler.SparkInvocationHandler;
import com.spring.data.spark.sparkdata.library.invocationHandler.SparkTransformation;
import com.spring.data.spark.sparkdata.library.repository.SparkRepository;
import com.spring.data.spark.sparkdata.library.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library.wordsresolver.WordResolverImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;


public class SparkInvocationHandlerFactory {

    private Map<String, TransformationSpider> transformationSpiderMap;

    private DataExtractorResolver resolver;


    private WordResolver wordResolver = new WordResolverImpl();


    public SparkInvocationHandler createSparkInvocationHandler(Class<? extends SparkRepository> sparkRepositoryImpl) {
        Class<?> modelClass = getModelClass(sparkRepositoryImpl);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(sparkRepositoryImpl);
        DataExtractor dataExtractor = resolver.resolve(pathToData);
        Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();
        Method [] methods = sparkRepositoryImpl.getMethods();
        List<SparkTransformation> transformations = new ArrayList<>();
        for (Method method : methods) {
            TransformationSpider transformationSpider = null;
            List<String> methodWords = wordResolver.resolveJavaMethodWords(method.getName());
            while(methodWords.size() > 1) {
                String transformationSpiderName = wordResolver.findAndRemoveMatchingMethodNamesIfExists(transformationSpiderMap.keySet(), methodWords);
                if(!transformationSpiderName.isEmpty()) {
                    transformationSpider = transformationSpiderMap.get(transformationSpiderName); // here we will always switch to the valid strategy
                }
                transformations.add(transformationSpider.getTransformation(methodWords));

            }

        }

        return SparkInvocationHandler.builder()
                .model(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor).
                transformationChain(transformationChain).
                build(); // not complete

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
